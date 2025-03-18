package inheritance.tests.cyclic;

import inheritance.annotations.Mixin;

/**
 * Class C for cyclic inheritance
 * Creates cyclic structure with classes A and B
 * Inherits from class B through @Mixin annotation
 */
@Mixin(ClassB.class)
public class ClassC extends CyclicInterfaceRoot {
    private int callCounter = 0;
    
    /**
     * Implementation of method from interface
     * @return "C" + result of calling method from parent class
     */
    public String testMethod() {
        callCounter++;
        
        // Limit the number of calls to prevent infinite loop
        if (callCounter > 3) {
            return "C(stop)";
        }
        
        System.out.println("C.testMethod(): execution [" + callCounter + "]");
        return "C" + nextTestMethod();
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