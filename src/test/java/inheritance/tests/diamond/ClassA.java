package inheritance.tests.diamond;

/**
 * Base class A for diamond inheritance
 * Serves as the beginning of inheritance hierarchy
 */
public class ClassA extends DiamondInterfaceRoot {
    /**
     * Implementation of method from interface
     * @return "A" - class identifier
     */
    public String testMethod() {
        System.out.println("A.testMethod(): execution");
        return "A";
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
} 