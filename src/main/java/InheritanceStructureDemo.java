import example.diamond.*;
import inheritance.factory.MixinFactory;

/**
 * Demonstration of the inheritance structure in the project
 */
public class InheritanceStructureDemo {
    
    public static void main(String[] args) {
        boolean debug = false;
        
        for (String arg : args) {
            if (arg.equals("--debug=true")) {
                debug = true;
                MixinFactory.setDebugEnabled(true);
            }
        }
        
        System.out.println("=== Inheritance Structure Demonstration ===");
        
        // Create an instance of class F
        F f = MixinFactory.createInstance(F.class);
        
        // Print the inheritance structure
        System.out.println("\n=== Inheritance Structure of Class F ===");
        printInheritanceStructure(f);
        
        System.out.println("\n=== Demonstration Completed ===");
    }
    
    /**
     * Prints the inheritance structure for an object
     */
    private static void printInheritanceStructure(Object obj) {
        if (obj == null) {
            System.out.println("Object is null");
            return;
        }
        
        Class<?> clazz = obj.getClass();
        System.out.println("Class: " + clazz.getSimpleName());
        
        try {
            // Get the parent field
            java.lang.reflect.Field parentField = null;
            Class<?> currentClass = clazz;
            
            while (currentClass != null && parentField == null) {
                try {
                    parentField = currentClass.getDeclaredField("parent");
                } catch (NoSuchFieldException e) {
                    currentClass = currentClass.getSuperclass();
                }
            }
            
            if (parentField != null) {
                parentField.setAccessible(true);
                Object parent = parentField.get(obj);
                
                System.out.println("  ├── Parent: " + (parent != null ? parent.getClass().getSimpleName() : "null"));
                
                if (parent != null) {
                    printParentChain(parent, "  │   ");
                }
            } else {
                System.out.println("  ├── No parent field found");
            }
            
            // Print implemented interfaces
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                System.out.println("  └── Implements interfaces:");
                for (int i = 0; i < interfaces.length; i++) {
                    boolean isLast = (i == interfaces.length - 1);
                    System.out.println("      " + (isLast ? "└── " : "├── ") + interfaces[i].getSimpleName());
                }
            }
        } catch (Exception e) {
            System.out.println("  └── Error getting parent: " + e.getMessage());
        }
    }
    
    /**
     * Recursively prints the parent chain
     */
    private static void printParentChain(Object obj, String indent) {
        if (obj == null) {
            return;
        }
        
        try {
            // Get the parent field
            java.lang.reflect.Field parentField = null;
            Class<?> clazz = obj.getClass();
            Class<?> currentClass = clazz;
            
            while (currentClass != null && parentField == null) {
                try {
                    parentField = currentClass.getDeclaredField("parent");
                } catch (NoSuchFieldException e) {
                    currentClass = currentClass.getSuperclass();
                }
            }
            
            if (parentField != null) {
                parentField.setAccessible(true);
                Object parent = parentField.get(obj);
                
                System.out.println(indent + "├── Parent: " + (parent != null ? parent.getClass().getSimpleName() : "null"));
                
                if (parent != null) {
                    printParentChain(parent, indent + "│   ");
                }
            }
            
            // Print implemented interfaces
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            if (interfaces.length > 0) {
                System.out.println(indent + "└── Implements interfaces:");
                for (int i = 0; i < interfaces.length; i++) {
                    boolean isLast = (i == interfaces.length - 1);
                    System.out.println(indent + "    " + (isLast ? "└── " : "├── ") + interfaces[i].getSimpleName());
                }
            }
        } catch (Exception e) {
            System.out.println(indent + "└── Error: " + e.getMessage());
        }
    }
} 