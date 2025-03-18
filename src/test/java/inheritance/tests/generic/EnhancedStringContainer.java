package inheritance.tests.generic;

import inheritance.annotations.Mixin;

/**
 * Class inheriting from StringContainer through @Mixin
 * Extends functionality of the base class by adding conversion to uppercase
 */
@Mixin(StringContainer.class)
public class EnhancedStringContainer extends GenericInterfaceRoot<String> {
    
    /**
     * Returns value from the parent class
     */
    @Override
    public String getValue() {
        // Delegate call to the parent class
        return nextGetValue();
    }
    
    /**
     * Sets value by calling the parent class method
     */
    @Override
    public void setValue(String value) {
        // Delegate call to the parent class
        nextSetValue(value);
    }
    
    /**
     * Transforms value, adding functionality to the parent class method
     * Converts the result to uppercase
     */
    @Override
    public String transformValue(String prefix) {
        // First get the result from the parent class
        String parentResult = nextTransformValue(prefix);
        // Then convert it to uppercase
        return parentResult.toUpperCase();
    }
    
    /**
     * Additional method specific to this class
     * @return Value in lowercase
     */
    public String getLowerCaseValue() {
        String value = getValue();
        return value != null ? value.toLowerCase() : "null";
    }
} 