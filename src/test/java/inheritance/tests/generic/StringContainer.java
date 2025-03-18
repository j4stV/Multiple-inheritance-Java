package inheritance.tests.generic;

/**
 * Базовый класс, реализующий дженерик-интерфейс с типом String
 * Используется как начало цепочки наследования
 */
public class StringContainer extends GenericInterfaceRoot<String> {
    private String value;
    
    /**
     * Возвращает текущее значение
     */
    @Override
    public String getValue() {
        return value;
    }
    
    /**
     * Устанавливает новое значение
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * Трансформирует значение, добавляя указанный префикс
     */
    @Override
    public String transformValue(String prefix) {
        return prefix + ": " + (value != null ? value : "null");
    }
} 