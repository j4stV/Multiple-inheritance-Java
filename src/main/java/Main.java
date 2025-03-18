import example.diamond.*;
import inheritance.factory.MixinFactory;

/**
 * Main class for demonstrating the diamond inheritance mechanism
 */
public class Main {

    /**
     * Program entry point
     *
     * @param args command line arguments
     *             Supported arguments:
     *             --debug=true|false - enable/disable debug information
     */
    public static void main(String[] args) {
        System.out.println("=== Demonstration of multiple inheritance mechanism in Java ===");

        // Process command line arguments
        processArgs(args);
        
        try {
            // Run diamond inheritance test
            System.out.println("=== Starting diamond inheritance test ===");
            
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
            
            System.out.println("\n=== Diamond inheritance test completed ===");

        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== Demonstration completed ===");
    }

    /**
     * Process command line arguments
     *
     * @param args array of arguments
     */
    private static void processArgs(String[] args) {
        if (args == null || args.length == 0) {
            return;
        }

        for (String arg : args) {
            if (arg.startsWith("--debug=")) {
                String value = arg.substring("--debug=".length());
                boolean debug = Boolean.parseBoolean(value);
                MixinFactory.setDebugEnabled(debug);
                System.out.println("Debug information " + (debug ? "enabled" : "disabled"));
            }
        }
    }
}