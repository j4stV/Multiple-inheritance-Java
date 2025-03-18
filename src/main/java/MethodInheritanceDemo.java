import example.diamond.*;
import inheritance.annotations.Mixin;
import inheritance.factory.MixinFactory;

/**
 * Class for demonstrating parent class method calls
 * through the inheritance chain.
 */
public class MethodInheritanceDemo {
    
    public static void main(String[] args) {
        boolean debug = false;
        
        for (String arg : args) {
            if (arg.equals("--debug=true")) {
                debug = true;
                MixinFactory.setDebugEnabled(true);
            }
        }
        
        System.out.println("=== Method Inheritance Demonstration ===");
        
        // Create an instance of class F
        F f = MixinFactory.createInstance(F.class);
        
        System.out.println("\n=== Test for accessibility of all parent class methods ===");
        
        // Call method from class F
        System.out.println("\nMethod F.methodF():");
        f.methodF();
        
        // Call method from class D (accessible via parent)
        System.out.println("\nMethod D.methodD():");
        if (f.getParent() instanceof D) {
            ((D)f.getParent()).methodD();
        } else {
            System.out.println("ERROR: F's parent is not an instance of class D");
        }
        
        // Call method from class B (accessible via f -> D -> B)
        testMethodAccess(f, "B.methodB()", () -> {
            if (f.getParent() instanceof D) {
                D d = (D) f.getParent();
                if (d.getParent() instanceof B) {
                    ((B)d.getParent()).methodB();
                } else {
                    System.out.println("ERROR: D's parent is not an instance of class B");
                }
            } else {
                System.out.println("ERROR: D is not accessible from F");
            }
        });
        
        // Call method from class C (accessible via f -> D -> C)
        testMethodAccess(f, "C.methodC()", () -> {
            if (f.getParent() instanceof D) {
                D d = (D) f.getParent();
                // Try to find C in inheritance chain
                Object current = d;
                while (current != null) {
                    if (current instanceof C) {
                        ((C)current).methodC();
                        return;
                    }
                    try {
                        java.lang.reflect.Field parentField = 
                            getParentField(current.getClass());
                        if (parentField != null) {
                            parentField.setAccessible(true);
                            current = parentField.get(current);
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR: Could not access parent: " + e.getMessage());
                        break;
                    }
                }
                System.out.println("ERROR: C is not accessible in inheritance chain starting from D");
            } else {
                System.out.println("ERROR: D is not accessible from F");
            }
        });
        
        // Call method from class A (accessible via f -> D -> B/C -> A)
        testMethodAccess(f, "A.methodA()", () -> {
            if (f.getParent() instanceof D) {
                D d = (D) f.getParent();
                if (d.getParent() instanceof B) {
                    B b = (B) d.getParent();
                    if (b.getParent() instanceof A) {
                        ((A)b.getParent()).methodA();
                    } else {
                        System.out.println("ERROR: A is not accessible from B");
                    }
                } else {
                    System.out.println("ERROR: B is not accessible from D");
                }
            } else {
                System.out.println("ERROR: D is not accessible from F");
            }
        });
        
        System.out.println("\n=== Parent chain for class F ===");
        printParentChain(f);
    }
    
    private static void testMethodAccess(Object obj, String methodName, Runnable methodCall) {
        System.out.println("\nMethod " + methodName + ":");
        try {
            methodCall.run();
        } catch (Exception e) {
            System.out.println("ERROR: Exception when calling " + methodName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void printParentChain(Object obj) {
        if (obj == null) {
            System.out.println("Object is null");
            return;
        }
        
        System.out.println("Current object: " + obj.getClass().getName());
        
        try {
            printParentChainRecursive(obj, "    ");
        } catch (Exception e) {
            System.out.println("Error printing parent chain: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void printParentChainRecursive(Object obj, String indent) {
        if (obj == null) {
            return;
        }
        
        try {
            java.lang.reflect.Field parentField = getParentField(obj.getClass());
            if (parentField != null) {
                parentField.setAccessible(true);
                Object parent = parentField.get(obj);
                
                if (parent != null) {
                    System.out.println(indent + "↓ Parent: " + parent.getClass().getName());
                    
                    // Print implemented interfaces
                    Class<?>[] interfaces = parent.getClass().getInterfaces();
                    if (interfaces.length > 0) {
                        System.out.print(indent + "  Interfaces: ");
                        for (int i = 0; i < interfaces.length; i++) {
                            System.out.print(interfaces[i].getSimpleName());
                            if (i < interfaces.length - 1) {
                                System.out.print(", ");
                            }
                        }
                        System.out.println();
                    }
                    
                    // Continue recursively
                    printParentChainRecursive(parent, indent + "    ");
                } else {
                    System.out.println(indent + "↓ End of chain (parent is null)");
                }
            } else {
                System.out.println(indent + "↓ End of chain (no parent field)");
            }
        } catch (Exception e) {
            System.out.println(indent + "Error accessing parent: " + e.getMessage());
        }
    }
    
    private static java.lang.reflect.Field getParentField(Class<?> clazz) {
        try {
            return clazz.getSuperclass().getDeclaredField("parent");
        } catch (Exception e) {
            // No parent field exists
            return null;
        }
    }
} 