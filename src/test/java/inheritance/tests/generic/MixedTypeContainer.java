package inheritance.tests.generic;

import inheritance.annotations.Mixin;

/**
 * Класс, демонстрирующий наследование от классов с разными параметрами типа
 * Наследуется от EnhancedStringContainer и имеет доступ к функциональности IntegerContainer
 */
@Mixin({EnhancedStringContainer.class, IntegerContainer.class})
public class MixedTypeContainer extends GenericInterfaceRoot<String> {
    private Integer numericValue;
    
    /**
     * Получает строковое значение от родительского класса
     */
    @Override
    public String getValue() {
        return nextGetValue();
    }
    
    /**
     * Устанавливает строковое значение через родительский класс
     */
    @Override
    public void setValue(String value) {
        nextSetValue(value);
        
        // Пытаемся преобразовать строку в число и сохранить
        try {
            numericValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            numericValue = null;
        }
    }
    
    /**
     * Трансформирует значение, комбинируя функциональность родительского класса
     * и добавляя информацию о числовом значении, если доступно
     */
    @Override
    public String transformValue(String prefix) {
        String textResult = nextTransformValue(prefix);
        
        // Добавляем информацию о числовом значении, если оно доступно
        if (numericValue != null) {
            return textResult + " [Числовое значение: " + numericValue + "]";
        }
        
        return textResult;
    }
    
    /**
     * Доступ к числовой функциональности
     * @return Числовое значение, хранящееся в этом контейнере
     */
    public Integer getNumericValue() {
        return numericValue;
    }
    
    /**
     * Комбинированный метод, использующий функциональность обоих родительских классов
     * @param multiplier Множитель для числового значения
     * @return Строка, содержащая информацию о текстовом и числовом значениях
     */
    public String getCombinedInfo(int multiplier) {
        String lowerCase = "";
        
        // Вызываем метод родительского класса, если parent является EnhancedStringContainer
        if (parent instanceof EnhancedStringContainer) {
            EnhancedStringContainer enhancedParent = (EnhancedStringContainer) parent;
            lowerCase = enhancedParent.getLowerCaseValue();
        }
        
        // Формируем строку с информацией
        StringBuilder result = new StringBuilder();
        result.append("Текст: ").append(getValue());
        result.append(", В нижнем регистре: ").append(lowerCase);
        
        if (numericValue != null) {
            // Вычисляем произведение
            Integer multiplied = numericValue * multiplier;
            result.append(", Числовое значение: ").append(numericValue);
            result.append(", После умножения на ").append(multiplier).append(": ").append(multiplied);
        } else {
            result.append(", Числовое значение отсутствует");
        }
        
        return result.toString();
    }
} 