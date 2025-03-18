package inheritance.tests.repeatedAncestor;

import inheritance.annotations.Mixin;

/**
 * Class D for test with repeated ancestor
 * Inherits from classes A and B through @Mixin annotation
 * Class A is a common ancestor - both directly and through B
 */
@Mixin({ClassA.class, ClassB.class})
public class ClassD extends RepeatedAncestorInterfaceRoot {
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
     * Method for checking access to parent classes' methods
     * @return Combination of results from calling parent-specific methods
     */
    public String callParentSpecificMethods() {
        // Form the result of calls
        StringBuilder result = new StringBuilder();
        
        // Check access to class A method
        if (parent instanceof ClassA) {
            ClassA parentA = (ClassA) parent;
            result.append(parentA.methodA()).append("-");
        }
        
        // Check access to class B method
        if (parent instanceof ClassB) {
            ClassB parentB = (ClassB) parent;
            result.append(parentB.methodB());
        }
        
        return result.toString();
    }
} 