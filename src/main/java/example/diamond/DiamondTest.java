package example.diamond;

/**
 * Тестовый класс для проверки ромбовидного наследования
 */
public class DiamondTest {
    
    /**
     * Точка входа для прямого запуска теста
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println("DiamondTest main method executing...");
        runTest();
        System.out.println("DiamondTest main method completed.");
    }
    
    /**
     * Запускает тест ромбовидного наследования
     */
    public static void runTest() {
        System.out.println("=== Запуск теста ромбовидного наследования ===");
        
        try {
            // Создаем экземпляр класса D через фабрику
            System.out.println("Creating instance of class D...");
            D d = SomeInterfaceRoot.createInstance(D.class);
            System.out.println("Instance of class D created successfully.");
            
            // Демонстрация иерархии наследования
            System.out.println("Showing mixin hierarchy...");
            d.showMixinHierarchy();
            
            // Демонстрация цепного вызова методов
            System.out.println("\n=== Демонстрация цепного вызова методов ===");
            System.out.println("Calling d.method()...");
            d.method();
            
            // Демонстрация вызова уникальных методов
            System.out.println("\n=== Демонстрация вызова уникальных методов ===");
            System.out.println("1. Получаем доступ к классу B через рефлексию:");
            try {
                java.lang.reflect.Field parentField = d.getClass().getSuperclass().getDeclaredField("parent");
                parentField.setAccessible(true);
                Object parentB = parentField.get(d);
                System.out.println("Parent object class: " + (parentB != null ? parentB.getClass().getName() : "null"));
                if (parentB instanceof B) {
                    B b = (B) parentB;
                    System.out.println("Calling b.methodB()...");
                    b.methodB();
                } else {
                    System.out.println("Error: parent object is not an instance of class B");
                }
            } catch (Exception e) {
                System.out.println("Error accessing class B: " + e.getMessage());
                e.printStackTrace();
            }
            
            System.out.println("\n2. Получаем доступ к классу C через рефлексию:");
            try {
                java.lang.reflect.Field firstParentField = d.getClass().getSuperclass().getDeclaredField("parent");
                firstParentField.setAccessible(true);
                Object parentB = firstParentField.get(d);
                System.out.println("First parent object class: " + (parentB != null ? parentB.getClass().getName() : "null"));
                
                java.lang.reflect.Field secondParentField = parentB.getClass().getSuperclass().getDeclaredField("parent");
                secondParentField.setAccessible(true);
                Object parentC = secondParentField.get(parentB);
                System.out.println("Second parent object class: " + (parentC != null ? parentC.getClass().getName() : "null"));
                
                if (parentC instanceof C) {
                    C c = (C) parentC;
                    System.out.println("Calling c.methodC()...");
                    c.methodC();
                } else {
                    System.out.println("Error: second parent object is not an instance of class C");
                }
            } catch (Exception e) {
                System.out.println("Error accessing class C: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error during test execution: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Тест ромбовидного наследования завершен ===");
    }
} 