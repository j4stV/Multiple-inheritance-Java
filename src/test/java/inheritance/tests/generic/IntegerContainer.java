package inheritance.tests.generic;

/**
 * Класс, реализующий дженерик-интерфейс с типом Integer
 * Демонстрирует работу с другим типом данных
 */
public class IntegerContainer extends GenericInterfaceRoot<Integer> {
    private Integer value;
    
    /**
     * Возвращает текущее целочисленное значение
     */
    @Override
    public Integer getValue() {
        return value;
    }
    
    /**
     * Устанавливает новое целочисленное значение
     */
    @Override
    public void setValue(Integer value) {
        this.value = value;
    }
    
    /**
     * Трансформирует значение, добавляя указанный префикс
     */
    @Override
    public String transformValue(String prefix) {
        return prefix + " (число): " + (value != null ? value : "null");
    }
    
    /**
     * Дополнительный метод для работы с числовыми значениями
     * @param multiplier Множитель
     * @return Результат умножения текущего значения на множитель
     */
    public Integer multiply(int multiplier) {
        return value != null ? value * multiplier : null;
    }
} 