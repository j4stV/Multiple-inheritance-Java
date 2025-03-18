package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Class B - first class in the diamond inheritance hierarchy
 */
@Mixin(A.class)
public class B extends SomeInterfaceRoot {
    
    @Override
    public void method() {
        System.out.println("B.method(): Execution start");
        
        // Call method through inheritance chain
        nextMethod();
        
        System.out.println("B.method(): Execution end");
    }
    
    /**
     * Unique method of class B
     */
    public void methodB() {
        System.out.println("B.methodB(): Executing unique method of class B");
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
} 