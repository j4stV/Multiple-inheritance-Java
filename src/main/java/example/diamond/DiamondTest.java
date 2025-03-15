package example.diamond;

/**
 * Тестовый класс для проверки ромбовидного наследования
 */
public class DiamondTest {
    
    /**
     * Запускает тест ромбовидного наследования
     */
    public static void runTest() {
        System.out.println("=== Запуск теста ромбовидного наследования ===");
        
        // Создаем экземпляр класса D через фабрику
        D d = SomeInterfaceRoot.createInstance(D.class);
        
        // Демонстрация иерархии наследования
        d.showMixinHierarchy();
        
        // Демонстрация цепного вызова методов
        System.out.println("\n=== Демонстрация цепного вызова методов ===");
        d.method();
        
        // Демонстрация вызова уникальных методов
        System.out.println("\n=== Демонстрация вызова уникальных методов ===");
        System.out.println("1. Получаем доступ к классу B через рефлексию:");
        try {
            java.lang.reflect.Field parentField = d.getClass().getSuperclass().getDeclaredField("parent");
            parentField.setAccessible(true);
            Object parentB = parentField.get(d);
            if (parentB instanceof B) {
                B b = (B) parentB;
                b.methodB();
            } else {
                System.out.println("Ошибка: родительский объект не является экземпляром класса B");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении доступа к классу B: " + e.getMessage());
        }
        
        System.out.println("\n2. Получаем доступ к классу C через рефлексию:");
        try {
            java.lang.reflect.Field firstParentField = d.getClass().getSuperclass().getDeclaredField("parent");
            firstParentField.setAccessible(true);
            Object parentB = firstParentField.get(d);
            
            java.lang.reflect.Field secondParentField = parentB.getClass().getSuperclass().getDeclaredField("parent");
            secondParentField.setAccessible(true);
            Object parentC = secondParentField.get(parentB);
            
            if (parentC instanceof C) {
                C c = (C) parentC;
                c.methodC();
            } else {
                System.out.println("Ошибка: второй родительский объект не является экземпляром класса C");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении доступа к классу C: " + e.getMessage());
        }
        
        System.out.println("\n=== Тест ромбовидного наследования завершен ===");
    }
} 