package inheritance.tests.generic;

/**
 * Base class implementing generic interface with String type
 * Used as the beginning of inheritance chain
 */
public class StringContainer extends GenericInterfaceRoot<String> {
    private String value;
    
    /**
     * Returns the current value
     */
    @Override
    public String getValue() {
        return value;
    }
    
    /**
     * Sets a new value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * Transforms the value by adding the specified prefix
     */
    @Override
    public String transformValue(String prefix) {
        return prefix + ": " + (value != null ? value : "null");
    }
} 