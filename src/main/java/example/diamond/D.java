package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Class D demonstrates diamond inheritance by combining classes B and C
 */
@Mixin({B.class, C.class, E.class})
public class D extends SomeInterfaceRoot {

    @Override
    public void method() {
        System.out.println("D.method(): Execution start");

        // Call method through inheritance chain
        nextMethod();

        System.out.println("D.method(): Execution end");
    }

    /**
     * Unique method of class D
     */
    public void methodD() {
        System.out.println("D.methodD(): Unique method of class D");
        
        // Call parent class method (B)
        if (parent != null) {
            System.out.println("D.methodD(): Calling methods of available parents:");
            
            try {
                System.out.println("  Trying to call methodB() on parent " + 
                    parent.getClass().getSimpleName());
                if (parent instanceof B) {
                    ((B)parent).methodB();
                } else {
                    System.out.println("  Parent is not an instance of class B");
                }
            } catch (Exception e) {
                System.out.println("  Error calling methodB(): " + e.getMessage());
            }
            
            try {
                System.out.println("  Trying to call methodC() on parent " + 
                    parent.getClass().getSimpleName());
                if (parent instanceof C) {
                    ((C)parent).methodC();
                } else {
                    System.out.println("  Parent is not an instance of class C");
                }
            } catch (Exception e) {
                System.out.println("  Error calling methodC(): " + e.getMessage());
            }
            
            try {
                System.out.println("  Trying to call methodE() on parent " + 
                    parent.getClass().getSimpleName());
                if (parent instanceof E) {
                    ((E)parent).methodE();
                } else {
                    System.out.println("  Parent is not an instance of class E");
                }
            } catch (Exception e) {
                System.out.println("  Error calling methodE(): " + e.getMessage());
            }
        }
    }

    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }

    /**
     * Method for displaying inheritance hierarchy
     */
    public void showMixinHierarchy() {
        System.out.println("\nInheritance hierarchy for D:");
        System.out.println("D");

        if (parent != null) {
            showParentHierarchy(parent, "  ");
        }
    }

    /**
     * Recursively displays parent classes
     *
     * @param parent Parent object to analyze
     * @param indent Indentation for formatting output
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
            System.out.println(indent + "Error getting hierarchy: " + e.getMessage());
        }
    }
} 