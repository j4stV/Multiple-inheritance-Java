package inheritance.tests.diamond;

import inheritance.annotations.Mixin;

/**
 * Class C for diamond inheritance
 * Inherits from class A through @Mixin annotation
 */
@Mixin(ClassA.class)
public class ClassC extends DiamondInterfaceRoot {
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
    
    /**
     * Unique method of class C
     * @return String identifying class C
     */
    public String methodC() {
        return "C-specific";
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
} 