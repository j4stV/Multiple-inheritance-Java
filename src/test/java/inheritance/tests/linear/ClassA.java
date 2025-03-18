package inheritance.tests.linear;

/**
 * Base class A for linear inheritance
 * Implements TestLinearInterface and serves as the beginning of inheritance chain
 */
public class ClassA extends TestLinearInterfaceRoot {
    /**
     * Implementation of method from interface
     * @return "A" - class identifier
     */
    public String testMethod() {
        System.out.println("A.testMethod(): execution");
        return "A";
    }
} 