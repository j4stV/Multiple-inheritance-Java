package inheritance.tests.generic;

import inheritance.annotations.Mixin;

/**
 * Класс, наследующийся от StringContainer через @Mixin
 * Расширяет функциональность базового класса, добавляя преобразование к верхнему регистру
 */
@Mixin(StringContainer.class)
public class EnhancedStringContainer extends GenericInterfaceRoot<String> {
    
    /**
     * Возвращает значение из родительского класса
     */
    @Override
    public String getValue() {
        // Делегируем вызов родительскому классу
        return nextGetValue();
    }
    
    /**
     * Устанавливает значение, вызывая метод родительского класса
     */
    @Override
    public void setValue(String value) {
        // Делегируем вызов родительскому классу
        nextSetValue(value);
    }
    
    /**
     * Трансформирует значение, добавляя функциональность к методу родительского класса
     * Преобразует результат к верхнему регистру
     */
    @Override
    public String transformValue(String prefix) {
        // Сначала получаем результат из родительского класса
        String parentResult = nextTransformValue(prefix);
        // Затем преобразуем его к верхнему регистру
        return parentResult.toUpperCase();
    }
    
    /**
     * Дополнительный метод, специфичный для этого класса
     * @return Значение в нижнем регистре
     */
    public String getLowerCaseValue() {
        String value = getValue();
        return value != null ? value.toLowerCase() : "null";
    }
} 