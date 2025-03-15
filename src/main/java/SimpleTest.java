import example.diamond.*;

/**
 * Простой тестовый класс для проверки функциональности
 */
public class SimpleTest {
    
    public static void main(String[] args) {
        System.out.println("Starting simple test...");
        
        try {
            System.out.println("Creating instance of class D...");
            // Создаем экземпляр класса D и проверяем его тип
            D d = SomeInterfaceRoot.createInstance(D.class);
            
            System.out.println("Instance created: " + d);
            System.out.println("Instance class: " + d.getClass().getName());
            System.out.println("Is SomeInterface: " + (d instanceof SomeInterface));
            
            // Вызываем метод
            System.out.println("Calling method...");
            d.method();
            System.out.println("Method call completed.");
            
            // Проверяем структуру наследования через рефлексию
            System.out.println("Checking inheritance structure...");
            checkInheritanceStructure(d);
            System.out.println("Inheritance check completed.");
            
        } catch (Exception e) {
            System.out.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Simple test completed.");
    }
    
    private static void checkInheritanceStructure(D d) {
        System.out.println("\nChecking inheritance structure:");
        
        try {
            System.out.println("Getting parent field from D...");
            java.lang.reflect.Field parentField = d.getClass().getSuperclass().getDeclaredField("parent");
            parentField.setAccessible(true);
            Object parent1 = parentField.get(d);
            
            System.out.println("Level 1 - D.parent class: " + (parent1 != null ? parent1.getClass().getName() : "null"));
            
            if (parent1 != null) {
                System.out.println("Parent1 is instance of B: " + (parent1 instanceof B));
                System.out.println("Parent1 is instance of C: " + (parent1 instanceof C));
                System.out.println("Parent1 is instance of E: " + (parent1 instanceof E));
                
                try {
                    // Проверяем следующий уровень наследования
                    System.out.println("Getting parent field from level 1...");
                    java.lang.reflect.Field nextParentField = parent1.getClass().getSuperclass().getDeclaredField("parent");
                    nextParentField.setAccessible(true);
                    Object parent2 = nextParentField.get(parent1);
                    
                    System.out.println("\nLevel 2 - Next parent class: " + (parent2 != null ? parent2.getClass().getName() : "null"));
                    
                    if (parent2 != null) {
                        System.out.println("Parent2 is instance of A: " + (parent2 instanceof A));
                        System.out.println("Parent2 is instance of B: " + (parent2 instanceof B));
                        System.out.println("Parent2 is instance of C: " + (parent2 instanceof C));
                        System.out.println("Parent2 is instance of E: " + (parent2 instanceof E));
                        
                        try {
                            // Проверяем третий уровень наследования
                            System.out.println("Getting parent field from level 2...");
                            java.lang.reflect.Field nextParentField2 = parent2.getClass().getSuperclass().getDeclaredField("parent");
                            nextParentField2.setAccessible(true);
                            Object parent3 = nextParentField2.get(parent2);
                            
                            System.out.println("\nLevel 3 - Next parent class: " + (parent3 != null ? parent3.getClass().getName() : "null"));
                            
                            if (parent3 != null) {
                                System.out.println("Parent3 is instance of A: " + (parent3 instanceof A));
                                System.out.println("Parent3 is instance of B: " + (parent3 instanceof B));
                                System.out.println("Parent3 is instance of C: " + (parent3 instanceof C));
                                System.out.println("Parent3 is instance of E: " + (parent3 instanceof E));
                                
                                try {
                                    // Проверяем четвертый уровень наследования
                                    System.out.println("Getting parent field from level 3...");
                                    java.lang.reflect.Field nextParentField3 = parent3.getClass().getSuperclass().getDeclaredField("parent");
                                    nextParentField3.setAccessible(true);
                                    Object parent4 = nextParentField3.get(parent3);
                                    
                                    System.out.println("\nLevel 4 - Next parent class: " + (parent4 != null ? parent4.getClass().getName() : "null"));
                                    
                                    if (parent4 != null) {
                                        System.out.println("Parent4 is instance of A: " + (parent4 instanceof A));
                                        System.out.println("Parent4 is instance of B: " + (parent4 instanceof B));
                                        System.out.println("Parent4 is instance of C: " + (parent4 instanceof C));
                                        System.out.println("Parent4 is instance of E: " + (parent4 instanceof E));
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error checking level 4: " + e.getMessage());
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error checking level 3: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error checking level 2: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking inheritance: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 