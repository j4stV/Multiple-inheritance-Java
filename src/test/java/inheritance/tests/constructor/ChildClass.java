package inheritance.tests.constructor;

import inheritance.annotations.Mixin;

/**
 * Child class that calls nextConstructor
 */
@Mixin(BaseClass.class)
public class ChildClass extends ConstructorInterfaceRoot {
    private String prefix;
    
    /**
     * Constructor that calls nextConstructor
     * @param prefix prefix to add to value
     * @param value value to pass to parent
     */
    public ChildClass(String prefix, String value) {
        this.prefix = prefix;
        nextConstructor(value);
    }
    
    @Override
    public String getValue() {
        return prefix + " " + nextGetValue();
    }
} 