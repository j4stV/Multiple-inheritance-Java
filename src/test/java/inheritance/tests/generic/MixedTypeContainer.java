package inheritance.tests.generic;

import inheritance.annotations.Mixin;

/**
 * Class demonstrating inheritance from classes with different type parameters
 * Inherits from EnhancedStringContainer and has access to IntegerContainer functionality
 */
@Mixin({EnhancedStringContainer.class, IntegerContainer.class})
public class MixedTypeContainer extends GenericInterfaceRoot<String> {
    private Integer numericValue;
    
    /**
     * Gets string value from parent class
     */
    @Override
    public String getValue() {
        return nextGetValue();
    }
    
    /**
     * Sets string value through parent class
     */
    @Override
    public void setValue(String value) {
        nextSetValue(value);
        
        // Try to convert string to number and save
        try {
            numericValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            numericValue = null;
        }
    }
    
    /**
     * Transforms value, combining parent class functionality
     * and adding information about numeric value if available
     */
    @Override
    public String transformValue(String prefix) {
        String textResult = nextTransformValue(prefix);
        
        // Add information about numeric value if available
        if (numericValue != null) {
            return textResult + " [Numeric value: " + numericValue + "]";
        }
        
        return textResult;
    }
    
    /**
     * Access to numeric functionality
     * @return Numeric value stored in this container
     */
    public Integer getNumericValue() {
        return numericValue;
    }
    
    /**
     * Combined method using functionality of both parent classes
     * @param multiplier Multiplier for numeric value
     * @return String containing information about text and numeric values
     */
    public String getCombinedInfo(int multiplier) {
        String lowerCase = "";
        
        // Call parent class method if parent is EnhancedStringContainer
        if (parent instanceof EnhancedStringContainer) {
            EnhancedStringContainer enhancedParent = (EnhancedStringContainer) parent;
            lowerCase = enhancedParent.getLowerCaseValue();
        }
        
        // Form string with information
        StringBuilder result = new StringBuilder();
        result.append("Text: ").append(getValue());
        result.append(", In lowercase: ").append(lowerCase);
        
        if (numericValue != null) {
            // Calculate product
            Integer multiplied = numericValue * multiplier;
            result.append(", Numeric value: ").append(numericValue);
            result.append(", After multiplication by ").append(multiplier).append(": ").append(multiplied);
        } else {
            result.append(", Numeric value is not available");
        }
        
        return result.toString();
    }
} 