package inheritance.tests.linear;

import inheritance.annotations.Mixin;

/**
 * Class C for linear inheritance
 * Inherits from class B through @Mixin annotation
 */
@Mixin(ClassB.class)
public class ClassC extends TestLinearInterfaceRoot {
    /**
     * Implementation of method from interface
     * @return "C" + result of calling method from parent class
     */
    public String testMethod() {
        System.out.println("C.testMethod(): execution start");
        String result = "C" + nextTestMethod();
        System.out.println("C.testMethod(): execution end");
        return result;
    }
} 