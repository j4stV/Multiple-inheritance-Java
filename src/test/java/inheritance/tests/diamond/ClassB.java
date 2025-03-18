package inheritance.tests.diamond;

import inheritance.annotations.Mixin;

/**
 * Class B for diamond inheritance
 * Inherits from class A through @Mixin annotation
 */
@Mixin(ClassA.class)
public class ClassB extends DiamondInterfaceRoot {
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
     * Unique method of class B
     * @return String identifying class B
     */
    public String methodB() {
        return "B-specific";
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
} 