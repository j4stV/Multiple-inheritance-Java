import example.diamond.*;

/**
 * Example demonstrating complex multiple inheritance
 * with class F inheriting from both D and A simultaneously
 */
public class ComplexInheritanceExample {
    
    public static void main(String[] args) {
        System.out.println("=== Complex Multiple Inheritance Demonstration ===");
        
        try {
            // Create an instance of class F
            System.out.println("Creating an instance of class F...");
            F f = SomeInterfaceRoot.createInstance(F.class);
            System.out.println("Instance of class F created successfully.");
            
            // Check interfaces and types
            System.out.println("\nChecking interfaces and types:");
            System.out.println("f instanceof SomeInterface: " + (f instanceof SomeInterface));
            System.out.println("f instanceof F: " + (f instanceof F));
            // Note: f will not be an instance of D since Java doesn't have direct type inheritance
            
            // Call methods
            System.out.println("\nCalling methods:");
            System.out.println("Calling method f.method():");
            f.method();
            
            System.out.println("\nCalling unique method f.methodF():");
            f.methodF();
            
            // Display inheritance structure

            
            // Explore inheritance structure through reflection
            
        } catch (Exception e) {
            System.out.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Demonstration completed ===");
    }
    
    /**
     * Examines the inheritance structure of an object through reflection
     * @param f object to examine
     */
    private static void examineInheritanceStructure(F f) {
        try {
            // Get the first parent - should be D
            java.lang.reflect.Field parentField = f.getClass().getSuperclass().getDeclaredField("parent");
            parentField.setAccessible(true);
            Object firstParent = parentField.get(f);
            
            System.out.println("First parent of F: " + 
                    (firstParent != null ? firstParent.getClass().getName() : "null"));
            
            if (firstParent != null) {
                // Get the second parent - should be A (or null if none)
                java.lang.reflect.Field nextParentField = firstParent.getClass().getSuperclass().getDeclaredField("parent");
                nextParentField.setAccessible(true);
                Object secondParent = nextParentField.get(firstParent);
                
                System.out.println("Second parent: " + 
                        (secondParent != null ? secondParent.getClass().getName() : "null"));
                
                // If F inherits from D, which has a complex inheritance chain,
                // we'll see a deeper inheritance structure
                if (secondParent != null) {
                    java.lang.reflect.Field thirdParentField = secondParent.getClass().getSuperclass().getDeclaredField("parent");
                    thirdParentField.setAccessible(true);
                    Object thirdParent = thirdParentField.get(secondParent);
                    
                    System.out.println("Third parent: " + 
                            (thirdParent != null ? thirdParent.getClass().getName() : "null"));
                    
                    if (thirdParent != null) {
                        java.lang.reflect.Field fourthParentField = thirdParent.getClass().getSuperclass().getDeclaredField("parent");
                        fourthParentField.setAccessible(true);
                        Object fourthParent = fourthParentField.get(thirdParent);
                        
                        System.out.println("Fourth parent: " + 
                                (fourthParent != null ? fourthParent.getClass().getName() : "null"));
                        
                        if (fourthParent != null) {
                            java.lang.reflect.Field fifthParentField = fourthParent.getClass().getSuperclass().getDeclaredField("parent");
                            fifthParentField.setAccessible(true);
                            Object fifthParent = fifthParentField.get(fourthParent);
                            
                            System.out.println("Fifth parent: " + 
                                    (fifthParent != null ? fifthParent.getClass().getName() : "null"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error examining inheritance structure: " + e.getMessage());
        }
    }
} 