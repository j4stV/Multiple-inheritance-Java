package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Class E - third class in the diamond inheritance hierarchy
 */
@Mixin(A.class)
public class E extends SomeInterfaceRoot {

    @Override
    public void method() {
        System.out.println("E.method(): Execution start");

        // Call method through inheritance chain
        nextMethod();

        System.out.println("E.method(): Execution end");
    }

    /**
     * Unique method of class E
     */
    public void methodE() {
        System.out.println("E.methodE(): Executing unique method of class E");
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
}