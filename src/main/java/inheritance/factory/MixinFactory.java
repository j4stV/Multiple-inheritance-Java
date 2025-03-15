package inheritance.factory;

import inheritance.annotations.Mixin;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Фабрика для создания экземпляров классов с множественным наследованием.
 * Обрабатывает граф наследования, создает инстансы в правильном порядке 
 * и устанавливает связи между ними в виде цепочки наследования.
 */
public class MixinFactory {
    
    private static final Map<Class<?>, Object> instanceCache = new HashMap<>();
    
    /**
     * Создает экземпляр класса с установкой миксинов
     * @param clazz Класс для создания экземпляра
     * @param <T> Тип создаваемого экземпляра
     * @return Настроенный экземпляр класса
     */
    public static <T> T createInstance(Class<T> clazz) {
        return createInstanceWithMixins(clazz, new HashMap<>());
    }
    
    /**
     * Рекурсивно создает экземпляр класса с установкой всех миксинов
     * @param clazz Класс для создания экземпляра
     * @param instances Уже созданные экземпляры, чтобы избежать повторного создания
     * @param <T> Тип создаваемого экземпляра
     * @return Настроенный экземпляр класса
     */
    @SuppressWarnings("unchecked")
    private static <T> T createInstanceWithMixins(Class<T> clazz, Map<Class<?>, Object> instances) {
        // Проверяем, был ли уже создан экземпляр этого класса
        if (instances.containsKey(clazz)) {
            return (T) instances.get(clazz);
        }

        // Проверяем, является ли класс интерфейсом или абстрактным
        if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
            throw new RuntimeException("Невозможно создать экземпляр интерфейса или абстрактного класса: " + clazz.getName());
        }

        // Создаем экземпляр текущего класса
        T instance;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
            // Сохраняем созданный экземпляр, чтобы избежать циклических зависимостей
            instances.put(clazz, instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Не удалось создать экземпляр класса: " + clazz.getName(), e);
        }

        // Получаем аннотации @Mixin
        List<Class<?>> mixinClasses = getMixinClasses(clazz);
        
        if (mixinClasses.isEmpty()) {
            return instance;
        }

        // Создаем экземпляры всех миксинов в обратном порядке для правильной цепочки вызовов
        // Последний миксин в списке будет первым в цепочке вызовов
        Object previousParent = null;
        for (int i = mixinClasses.size() - 1; i >= 0; i--) {
            Class<?> mixinClass = mixinClasses.get(i);
            Object mixinInstance = createInstanceWithMixins(mixinClass, instances);
            
            // Если это первый миксин (последний в списке), он будет ссылаться на следующий родитель
            if (previousParent != null) {
                try {
                    Field parentField = findField(mixinClass, "parent");
                    if (parentField != null) {
                        parentField.setAccessible(true);
                        parentField.set(mixinInstance, previousParent);
                    }
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException("Ошибка при установке родительского поля для миксина", e);
                }
            }
            
            // Сохраняем текущий миксин как предыдущий для следующей итерации
            previousParent = mixinInstance;
        }

        // Устанавливаем первый миксин в цепочке (последний обработанный) как parent для основного класса
        try {
            Field parentField = findField(clazz, "parent");
            if (parentField != null) {
                parentField.setAccessible(true);
                parentField.set(instance, previousParent);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Ошибка при установке родительского поля", e);
        }

        return instance;
    }
    
    /**
     * Находит поле с заданным именем, включая поля в родительских классах
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
     * Очищает кэш инстансов. Может быть полезно при юнит-тестировании.
     */
    public static void clearCache() {
        instanceCache.clear();
    }
} 