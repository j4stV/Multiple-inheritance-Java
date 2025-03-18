package inheritance.tests.generic;

/**
 * Class implementing generic interface with Integer type
 * Demonstrates working with a different data type
 */
public class IntegerContainer extends GenericInterfaceRoot<Integer> {
    private Integer value;
    
    /**
     * Returns the current integer value
     */
    @Override
    public Integer getValue() {
        return value;
    }
    
    /**
     * Sets a new integer value
     */
    @Override
    public void setValue(Integer value) {
        this.value = value;
    }
    
    /**
     * Transforms the value by adding the specified prefix
     */
    @Override
    public String transformValue(String prefix) {
        return prefix + " (number): " + (value != null ? value : "null");
    }
    
    /**
     * Additional method for working with numeric values
     * @param multiplier Multiplier
     * @return Result of multiplying current value by the multiplier
     */
    public Integer multiply(int multiplier) {
        return value != null ? value * multiplier : null;
    }
} 