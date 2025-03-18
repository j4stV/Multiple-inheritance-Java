package inheritance.tests.repeatedAncestor;

/**
 * Base class A for test with repeated ancestor
 * Implements RepeatedAncestorInterface and serves as the beginning of inheritance chain
 */
public class ClassA extends RepeatedAncestorInterfaceRoot {
    /**
     * Implementation of method from interface
     * @return "A" - class identifier
     */
    public String testMethod() {
        System.out.println("A.testMethod(): execution");
        return "A";
    }
    
    /**
     * Method specific to class A
     * @return String indicating execution of method in class A
     */
    public String methodA() {
        return "A-specific";
    }
} 