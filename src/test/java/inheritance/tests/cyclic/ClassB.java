package inheritance.tests.cyclic;

import inheritance.annotations.Mixin;

/**
 * Class B for cyclic inheritance
 * Creates cyclic structure with classes A and C
 * Inherits from class A through @Mixin annotation
 */
@Mixin(ClassA.class)
public class ClassB extends CyclicInterfaceRoot {
    private int callCounter = 0;
    
    /**
     * Implementation of method from interface
     * @return "B" + result of calling method from parent class
     */
    public String testMethod() {
        callCounter++;
        
        // Limit the number of calls to prevent infinite loop
        if (callCounter > 3) {
            return "B(stop)";
        }
        
        System.out.println("B.testMethod(): execution [" + callCounter + "]");
        return "B" + nextTestMethod();
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