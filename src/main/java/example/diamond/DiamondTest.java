package example.diamond;

/**
 * Test class for checking diamond inheritance
 */
public class DiamondTest {
    
    /**
     * Entry point for direct test execution
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("DiamondTest main method executing...");
        runTest();
        System.out.println("DiamondTest main method completed.");
    }
    
    /**
     * Runs the diamond inheritance test
     */
    public static void runTest() {
        System.out.println("=== Starting diamond inheritance test ===");
        
        try {
            // Create an instance of class D through the factory
            System.out.println("Creating instance of class D...");
            D d = SomeInterfaceRoot.createInstance(D.class);
            System.out.println("Instance of class D created successfully.");
            
            // Demonstration of inheritance hierarchy
            System.out.println("Showing mixin hierarchy...");
            d.showMixinHierarchy();
            
            // Demonstration of method chain call
            System.out.println("\n=== Demonstration of method chain call ===");
            System.out.println("Calling d.method()...");
            d.method();
            
            // Demonstration of unique methods call
            System.out.println("\n=== Demonstration of unique methods call ===");
            System.out.println("1. Accessing class B through reflection:");
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
            
            System.out.println("\n2. Accessing class C through reflection:");
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
        
        System.out.println("\n=== Diamond inheritance test completed ===");
    }
} 