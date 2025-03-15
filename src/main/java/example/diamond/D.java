package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс D демонстрирует ромбовидное наследование, объединяя классы B и C
 */
@Mixin({B.class, C.class})
public class D extends SomeInterfaceRoot {
    
    @Override
    public void method() {
        System.out.println("D.method(): Начало выполнения");
        
        // Вызываем метод через цепочку наследования
        nextMethod();
        
        System.out.println("D.method(): Конец выполнения");
    }
    
    /**
     * Метод для отображения иерархии наследования
     */
    public void showMixinHierarchy() {
        System.out.println("\nИерархия наследования для D:");
        System.out.println("D");
        
        if (parent != null) {
            showParentHierarchy(parent, "  ");
        }
    }
    
    /**
     * Рекурсивно отображает родительские классы
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
} 