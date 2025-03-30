package inheritance.tests.constructor;

import inheritance.annotations.Mixin;

/**
 * Bad child class that doesn't call nextConstructor
 */
@Mixin(BaseClass.class)
public class BadChildClass extends ConstructorInterfaceRoot {
    private String additionalInfo;
    
    /**
     * Constructor that does not call nextConstructor
     * This should cause an exception when instantiated
     */
    public BadChildClass(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        // Missing nextConstructor call!
    }
    
    @Override
    public String getValue() {
        return additionalInfo + " - " + nextGetValue();
    }
} 