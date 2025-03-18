package inheritance.tests.linear;

import inheritance.annotations.Mixin;

/**
 * Class B for linear inheritance
 * Inherits from class A through @Mixin annotation
 */
@Mixin(ClassA.class)
public class ClassB extends TestLinearInterfaceRoot {
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
} 