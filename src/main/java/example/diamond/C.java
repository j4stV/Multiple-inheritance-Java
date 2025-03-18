package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Class C - second class in the diamond inheritance hierarchy
 */
@Mixin(A.class)
public class C extends SomeInterfaceRoot {
    
    @Override
    public void method() {
        System.out.println("C.method(): Execution start");
        
        // Call method through inheritance chain
        nextMethod();
        
        System.out.println("C.method(): Execution end");
    }
    
    /**
     * Unique method of class C
     */
    public void methodC() {
        System.out.println("C.methodC(): Executing unique method of class C");
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
} 