import example.diamond.*;

/**
 * Simple test class for checking functionality
 */
public class SimpleTest {
    
    public static void main(String[] args) {
        System.out.println("Starting simple test...");
        
        try {
            System.out.println("Creating instance of class D...");
            // Create an instance of class D and check its type
            D d = SomeInterfaceRoot.createInstance(D.class);
            
            System.out.println("Instance created: " + d);
            System.out.println("Instance class: " + d.getClass().getName());
            System.out.println("Is SomeInterface: " + (d instanceof SomeInterface));
            
            // Call the method
            System.out.println("Calling method...");
            d.method();
            System.out.println("Method call completed.");
            
            // Check inheritance structure using reflection
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
                
                // Continue checking the inheritance chain if parent1 has its own parent
                if (parent1.getClass().getSuperclass() != null) {
                    try {
                        java.lang.reflect.Field parentField2 = parent1.getClass().getSuperclass().getDeclaredField("parent");
                        parentField2.setAccessible(true);
                        Object parent2 = parentField2.get(parent1);
                        
                        System.out.println("Level 2 - parent's parent class: " + (parent2 != null ? parent2.getClass().getName() : "null"));
                        
                        if (parent2 != null) {
                            System.out.println("Parent2 is instance of A: " + (parent2 instanceof A));
                            
                            // Check one more level if needed
                            if (parent2.getClass().getSuperclass() != null) {
                                try {
                                    java.lang.reflect.Field parentField3 = parent2.getClass().getSuperclass().getDeclaredField("parent");
                                    parentField3.setAccessible(true);
                                    Object parent3 = parentField3.get(parent2);
                                    
                                    System.out.println("Level 3 - grandparent's parent class: " + 
                                            (parent3 != null ? parent3.getClass().getName() : "null"));
                                } catch (NoSuchFieldException e) {
                                    System.out.println("No more parent fields at level 3.");
                                }
                            }
                        }
                    } catch (NoSuchFieldException e) {
                        System.out.println("No parent field found in parent1's class.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error during inheritance check: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 