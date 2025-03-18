package inheritance.tests.repeatedAncestor;

import inheritance.annotations.Mixin;

/**
 * Class B for test with repeated ancestor
 * Inherits from class A through @Mixin annotation
 */
@Mixin(ClassA.class)
public class ClassB extends RepeatedAncestorInterfaceRoot {
    /**
     * Implementation of method from interface
     * @return "B" + result of calling method from parent class
     */
    public String testMethod() {
        System.out.println("B.testMethod(): execution start");
        String result = "B" + nextTestMethod();
        System.out.println("B.testMethod(): execution end");
        return result;
    }
    
    /**
     * Method specific to class B
     * @return String indicating execution of method in class B
     */
    public String methodB() {
        return "B-specific";
    }
} 