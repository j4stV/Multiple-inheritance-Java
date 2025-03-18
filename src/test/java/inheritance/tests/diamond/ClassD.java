package inheritance.tests.diamond;

import inheritance.annotations.Mixin;

/**
 * Class D for diamond inheritance
 * Inherits from classes B and C through @Mixin annotation
 */
@Mixin({ClassB.class, ClassC.class})
public class ClassD extends DiamondInterfaceRoot {
    /**
     * Implementation of method from interface
     * @return "D" + result of calling method from parent class
     */
    public String testMethod() {
        System.out.println("D.testMethod(): execution start");
        String result = "D" + nextTestMethod();
        System.out.println("D.testMethod(): execution end");
        return result;
    }
    
    /**
     * Method for checking access to unique methods of parent classes
     * @return Result of calling specific method of parent class B
     */
    public String callParentSpecificMethods() {
        if (parent != null && parent instanceof ClassB) {
            ClassB parentB = (ClassB) parent;
            return parentB.methodB();
        }
        return "";
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
} 