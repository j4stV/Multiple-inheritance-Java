package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Class F - demonstration of complex multiple inheritance
 * Inherits both from D (with its complex inheritance chain D → B → C → E → A)
 * and directly from A, creating an alternative inheritance path.
 */
@Mixin({A.class, D.class})
public class F extends SomeInterfaceRoot {

    @Override
    public void method() {
        System.out.println("F.method(): Execution start");
        
        // Call method through inheritance chain
        nextMethod();
        
        System.out.println("F.method(): Execution end");
    }
    
    /**
     * Method for displaying complex inheritance structure
     */
    public void showComplexInheritance() {
        System.out.println("\nComplex inheritance structure for F:");
        System.out.println("F");
        
        if (parent != null) {
            showParentHierarchy(parent, "  ");
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
     * Recursively displays parent classes in the inheritance structure
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
    
    /**
     * Example of unique method of class F
     */
    public void methodF() {
        System.out.println("F.methodF(): Executing unique method of class F");
    }
} 