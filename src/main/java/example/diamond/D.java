package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс D демонстрирует ромбовидное наследование, объединяя классы B и C
 */
@Mixin({B.class, C.class, E.class})
public class D extends SomeInterfaceRoot {

    @Override
    public void method() {
        System.out.println("D.method(): Начало выполнения");

        // Вызываем метод через цепочку наследования
        nextMethod();

        System.out.println("D.method(): Конец выполнения");
    }

    /**
     * Уникальный метод класса D
     */
    public void methodD() {
        System.out.println("D.methodD(): Уникальный метод класса D");
        
        // Вызываем метод родительского класса (B)
        if (parent != null) {
            System.out.println("D.methodD(): Вызываем методы доступных родителей:");
            
            try {
                System.out.println("  Пытаемся вызвать methodB() у родителя " + 
                    parent.getClass().getSimpleName());
                if (parent instanceof B) {
                    ((B)parent).methodB();
                } else {
                    System.out.println("  Родитель не является экземпляром класса B");
                }
            } catch (Exception e) {
                System.out.println("  Ошибка при вызове methodB(): " + e.getMessage());
            }
            
            try {
                System.out.println("  Пытаемся вызвать methodC() у родителя " + 
                    parent.getClass().getSimpleName());
                if (parent instanceof C) {
                    ((C)parent).methodC();
                } else {
                    System.out.println("  Родитель не является экземпляром класса C");
                }
            } catch (Exception e) {
                System.out.println("  Ошибка при вызове methodC(): " + e.getMessage());
            }
            
            try {
                System.out.println("  Пытаемся вызвать methodE() у родителя " + 
                    parent.getClass().getSimpleName());
                if (parent instanceof E) {
                    ((E)parent).methodE();
                } else {
                    System.out.println("  Родитель не является экземпляром класса E");
                }
            } catch (Exception e) {
                System.out.println("  Ошибка при вызове methodE(): " + e.getMessage());
            }
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
     *
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