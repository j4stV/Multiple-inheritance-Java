package inheritance.tests.generic;

import inheritance.annotations.Mixin;

/**
 * Class demonstrating inheritance from classes with different type parameters
 * Inherits from EnhancedStringContainer and has access to IntegerContainer functionality
 */
@Mixin({EnhancedStringContainer.class, IntegerContainer.class})
public class MixedTypeContainer extends GenericInterfaceRoot<String> {
    private Integer numericValue;
    private String stringValue;
    
    /**
     * Gets string value from parent class
     */
    @Override
    public String getValue() {
        return stringValue;
    }
    
    /**
     * Sets string value through parent class.
     * Only calls EnhancedStringContainer's setValue to avoid type mismatch with IntegerContainer
     */
    @Override
    public void setValue(String value) {
        // Store value locally instead of passing to parents with incompatible types
        this.stringValue = value;
        
        // Call parent only if it's a StringContainer or EnhancedStringContainer
        if (parent instanceof EnhancedStringContainer) {
            EnhancedStringContainer stringParent = (EnhancedStringContainer) parent;
            stringParent.setValue(value);
        }
        
        // Try to convert string to number and save
        try {
            numericValue = Integer.parseInt(value);
            
            // If conversion to Integer succeeded, we can also set value in IntegerContainer parent
            // This would require finding the IntegerContainer parent in the chain
            // In a real implementation, this would be more complex
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
        // Get transformation from string parent
        String textResult = "";
        if (parent instanceof EnhancedStringContainer) {
            EnhancedStringContainer stringParent = (EnhancedStringContainer) parent;
            textResult = stringParent.transformValue(prefix);
        } else {
            // Fallback if parent is not available
            textResult = prefix.toUpperCase() + ": " + stringValue;
        }
        
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
        
        // Если у нас есть строковое значение, преобразуем его в нижний регистр
        // Это имитирует поведение метода getLowerCaseValue() класса EnhancedStringContainer
        if (stringValue != null) {
            lowerCase = stringValue.toLowerCase();
        }
        
        // Пытаемся получить значение через родителя, если это возможно
        if (parent instanceof EnhancedStringContainer) {
            EnhancedStringContainer enhancedParent = (EnhancedStringContainer) parent;
            // Устанавливаем значение в родителе, чтобы убедиться, что оно синхронизировано
            enhancedParent.setValue(stringValue);
            lowerCase = enhancedParent.getLowerCaseValue();
        }
        
        // Формируем строку с информацией
        StringBuilder result = new StringBuilder();
        result.append("Text: ").append(getValue());
        result.append(", In lowercase: ").append(lowerCase);
        
        if (numericValue != null) {
            // Вычисляем произведение
            Integer multiplied = numericValue * multiplier;
            result.append(", Numeric value: ").append(numericValue);
            result.append(", After multiplication by ").append(multiplier).append(": ").append(multiplied);
        } else {
            result.append(", Numeric value is not available");
        }
        
        return result.toString();
    }
} 