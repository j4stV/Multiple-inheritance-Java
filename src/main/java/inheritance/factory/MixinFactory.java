package inheritance.factory;

import inheritance.annotations.Mixin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Фабрика для создания экземпляров классов с множественным наследованием.
 * Использует алгоритм топологической сортировки для определения порядка
 * создания инстансов и установки связей между ними.
 */
public class MixinFactory {
    
    private static final Map<Class<?>, Object> instanceCache = new HashMap<>();
    private static boolean debugEnabled = true;  // Флаг для включения/отключения отладочной информации
    
    /**
     * Включает или выключает вывод отладочной информации
     * @param enabled true для включения, false для отключения
     */
    public static void setDebugEnabled(boolean enabled) {
        debugEnabled = enabled;
    }
    
    /**
     * Создает экземпляр класса с множественным наследованием
     * 
     * @param clazz Класс для создания экземпляра
     * @param <T> Тип создаваемого экземпляра
     * @return Настроенный экземпляр класса
     */
    public static <T> T createInstance(Class<T> clazz) {
        if (debugEnabled) {
            System.out.println("\n=== Создание экземпляра класса с множественным наследованием ===");
            System.out.println("Целевой класс: " + clazz.getName());
        }
        
        // Очищаем кэш для нового создания
        instanceCache.clear();
        
        // Строим граф наследования
        Map<Class<?>, List<Class<?>>> inheritanceGraph = buildInheritanceGraph(clazz);
        
        if (debugEnabled) {
            System.out.println("\n=== Граф наследования ===");
            for (Map.Entry<Class<?>, List<Class<?>>> entry : inheritanceGraph.entrySet()) {
                System.out.println(entry.getKey().getSimpleName() + " наследуется от: " + 
                    entry.getValue().stream()
                        .map(Class::getSimpleName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("<нет родителей>"));
            }
        }
        
        // Выполняем топологическую сортировку
        List<Class<?>> sortedClasses = topologicalSort(inheritanceGraph);
        
        if (debugEnabled) {
            System.out.println("\n=== Результат топологической сортировки ===");
            System.out.println("Порядок создания и линковки классов (от начального к конечным):");
            for (int i = 0; i < sortedClasses.size(); i++) {
                System.out.println((i + 1) + ". " + sortedClasses.get(i).getSimpleName());
            }
        }
        
        // Создаем экземпляры в порядке топологической сортировки
        createInstances(sortedClasses);
        
        // Устанавливаем связи между экземплярами в порядке топологической сортировки
        setupParentRelationships(sortedClasses);
        
        // Возвращаем созданный экземпляр запрашиваемого класса
        return (T) instanceCache.get(clazz);
    }
    
    /**
     * Строит граф наследования, начиная с указанного класса.
     * Для каждого класса определяется список его родителей из аннотации @Mixin.
     * 
     * @param startClass Начальный класс для построения графа
     * @return Граф наследования, где ключ - класс, значение - список его родителей
     */
    private static Map<Class<?>, List<Class<?>>> buildInheritanceGraph(Class<?> startClass) {
        Map<Class<?>, List<Class<?>>> graph = new HashMap<>();
        Queue<Class<?>> queue = new LinkedList<>();
        Set<Class<?>> visited = new HashSet<>();
        
        queue.add(startClass);
        visited.add(startClass);
        
        while (!queue.isEmpty()) {
            Class<?> currentClass = queue.poll();
            
            // Проверяем, что класс не интерфейс и не абстрактный
            if (currentClass.isInterface() || Modifier.isAbstract(currentClass.getModifiers())) {
                continue;
            }
            
            // Получаем родителей из аннотации @Mixin
            List<Class<?>> parents = getMixinClasses(currentClass);
            
            // Добавляем класс и его родителей в граф
            graph.put(currentClass, parents);
            
            // Добавляем родителей в очередь для обработки
            for (Class<?> parent : parents) {
                if (!visited.contains(parent) && !parent.isInterface() && !Modifier.isAbstract(parent.getModifiers())) {
                    queue.add(parent);
                    visited.add(parent);
                }
            }
        }
        
        return graph;
    }
    
    /**
     * Выполняет топологическую сортировку графа наследования.
     * Гарантирует, что родители будут располагаться в списке после своих потомков.
     * 
     * @param graph Граф наследования
     * @return Отсортированный список классов
     */
    private static List<Class<?>> topologicalSort(Map<Class<?>, List<Class<?>>> graph) {
        List<Class<?>> result = new ArrayList<>();
        Set<Class<?>> visited = new HashSet<>();
        Set<Class<?>> processing = new HashSet<>();
        
        for (Class<?> clazz : graph.keySet()) {
            if (!visited.contains(clazz)) {
                dfs(clazz, graph, visited, processing, result);
            }
        }
        
        return result;
    }
    
    /**
     * Вспомогательный метод для обхода в глубину при топологической сортировке.
     */
    private static void dfs(Class<?> current, Map<Class<?>, List<Class<?>>> graph, Set<Class<?>> visited, 
                            Set<Class<?>> processing, List<Class<?>> result) {
        if (processing.contains(current)) {
            throw new RuntimeException("Обнаружена циклическая зависимость в наследовании: " + current.getName());
        }
        
        if (visited.contains(current)) {
            return;
        }
        
        processing.add(current);
        
        // Обрабатываем родителей
        List<Class<?>> parents = graph.getOrDefault(current, Collections.emptyList());
        for (Class<?> parent : parents) {
            if (!parent.isInterface() && !Modifier.isAbstract(parent.getModifiers()) && !visited.contains(parent)) {
                dfs(parent, graph, visited, processing, result);
            }
        }
        
        visited.add(current);
        processing.remove(current);
        result.add(current);
    }
    
    /**
     * Создает экземпляры всех классов в порядке топологической сортировки.
     * 
     * @param sortedClasses Отсортированный список классов
     */
    private static void createInstances(List<Class<?>> sortedClasses) {
        for (Class<?> clazz : sortedClasses) {
            try {
                // Пропускаем интерфейсы и абстрактные классы
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                
                // Создаем экземпляр
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object instance = constructor.newInstance();
                
                // Добавляем в кэш
                instanceCache.put(clazz, instance);
                
                if (debugEnabled) {
                    System.out.println("Создан экземпляр класса: " + clazz.getSimpleName());
                }
                
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Ошибка при создании экземпляра класса: " + clazz.getName(), e);
            }
        }
    }
    
    /**
     * Устанавливает связи parent между созданными экземплярами в соответствии с результатом
     * топологической сортировки, создавая цепочку инстансов в порядке их создания.
     * 
     * @param sortedClasses Отсортированный список классов
     */
    private static void setupParentRelationships(List<Class<?>> sortedClasses) {
        if (debugEnabled) {
            System.out.println("\n=== Итоговый порядок наследования ===");
            System.out.println("(Классы связываются в порядке топологической сортировки)");
        }
        
        // Устанавливаем связи в обратном порядке (от последнего к первому в списке сортировки)
        // Это создаст цепочку F -> D -> E -> C -> B -> A
        
        if (sortedClasses.size() <= 1) {
            return; // Нечего связывать, если только один класс
        }
        
        // Перебираем классы от последнего к первому (кроме первого, так как ему не с чем связываться)
        for (int i = sortedClasses.size() - 1; i > 0; i--) {
            Class<?> currentClass = sortedClasses.get(i);
            Class<?> parentClass = sortedClasses.get(i - 1);
            
            Object currentInstance = instanceCache.get(currentClass);
            Object parentInstance = instanceCache.get(parentClass);
            
            if (currentInstance == null || parentInstance == null) {
                continue;
            }
            
            try {
                Field parentField = findField(currentClass, "parent");
                if (parentField != null) {
                    parentField.setAccessible(true);
                    parentField.set(currentInstance, parentInstance);
                    
                    if (debugEnabled) {
                        System.out.println("Установлена связь: " + currentClass.getSimpleName() + 
                                " -> " + parentClass.getSimpleName());
                    }
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Ошибка при установке родительского поля для " + currentClass.getName(), e);
            }
        }
        
        if (debugEnabled) {
            System.out.println("\n=== Структура наследования построена успешно ===");
        }
    }
    
    /**
     * Находит поле с заданным именем, включая поля в родительских классах
     * 
     * @param clazz Класс для поиска
     * @param fieldName Имя поля
     * @return Найденное поле или null
     */
    private static Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
    
    /**
     * Возвращает список классов, указанных в аннотациях @Mixin
     * 
     * @param clazz Класс для анализа
     * @return Список классов-миксинов
     */
    private static List<Class<?>> getMixinClasses(Class<?> clazz) {
        List<Class<?>> mixinClasses = new ArrayList<>();
        Mixin[] mixins = clazz.getAnnotationsByType(Mixin.class);
        
        if (mixins != null) {
            for (Mixin mixin : mixins) {
                // Добавляем все классы из массива value()
                for (Class<?> mixinClass : mixin.value()) {
                    mixinClasses.add(mixinClass);
                }
            }
        }
        
        return mixinClasses;
    }
    
    /**
     * Очищает кэш инстансов.
     */
    public static void clearCache() {
        instanceCache.clear();
    }
} 