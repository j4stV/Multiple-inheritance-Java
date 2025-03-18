package inheritance.tests.cyclic;

import inheritance.annotations.Mixin;

/**
 * Class A for cyclic inheritance
 * Creates cyclic structure with classes B and C
 * Inherits from class C through @Mixin annotation
 */
@Mixin(ClassC.class)
public class ClassA extends CyclicInterfaceRoot {
    private int callCounter = 0;
    
    /**
     * Implementation of method from interface
     * @return "A" + result of calling method from parent class
     */
    public String testMethod() {
        callCounter++;
        
        // Limit the number of calls to prevent infinite loop
        if (callCounter > 3) {
            return "A(stop)";
        }
        
        System.out.println("A.testMethod(): execution [" + callCounter + "]");
        return "A" + nextTestMethod();
    }
    
    /**
     * Gets the parent object
     * @return Parent object
     */
    public Object getParent() {
        return parent;
    }
    
    /**
     * Resets call counter for repeated tests
     */
    public void resetCallCounter() {
        callCounter = 0;
    }
} 