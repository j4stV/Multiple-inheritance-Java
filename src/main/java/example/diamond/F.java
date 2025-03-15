package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс F - демонстрация сложного множественного наследования
 * Наследуется как от D (с его сложной цепочкой наследования D → B → C → E → A),
 * так и напрямую от A, создавая альтернативный путь наследования.
 */
@Mixin({A.class, D.class})
public class F extends SomeInterfaceRoot {

    @Override
    public void method() {
        System.out.println("F.method(): Начало выполнения");
        
        // Вызываем метод через цепочку наследования
        nextMethod();
        
        System.out.println("F.method(): Конец выполнения");
    }
    
    /**
     * Метод для отображения сложной структуры наследования
     */
    public void showComplexInheritance() {
        System.out.println("\nКомплексная структура наследования для F:");
        System.out.println("F");
        
        if (parent != null) {
            showParentHierarchy(parent, "  ");
        }
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
    
    /**
     * Рекурсивно отображает родительские классы в структуре наследования
     * @param parent Родительский объект для анализа
     * @param indent Отступ для форматирования вывода
     */
    private void showParentHierarchy(Object parent, String indent) {
        if (parent == null) return;
        
        System.out.println(indent + parent.getClass().getSimpleName());
        
        try {
            java.lang.reflect.Field parentField = parent.getClass().getSuperclass().getDeclaredField("parent");
            parentField.setAccessible(true);
            Object nextParent = parentField.get(parent);
            if (nextParent != null) {
                showParentHierarchy(nextParent, indent + "  ");
            }
        } catch (Exception e) {
            System.out.println(indent + "Ошибка при получении иерархии: " + e.getMessage());
        }
    }
    
    /**
     * Пример уникального метода класса F
     */
    public void methodF() {
        System.out.println("F.methodF(): Выполнение уникального метода класса F");
    }
} 