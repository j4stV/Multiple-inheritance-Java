package example.diamond;

/**
 * Base class A - foundation for diamond inheritance
 */
public class A extends SomeInterfaceRoot {
    private int value = 0;

    public A() {
        nextMethod();
        System.out.println("A created");
        value++;
    }


    @Override
    public void method() {
        System.out.println(value);

        System.out.println("A.method(): Executing method in base class");
        nextMethod();
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
} 