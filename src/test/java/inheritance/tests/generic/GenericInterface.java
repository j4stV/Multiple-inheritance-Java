package inheritance.tests.generic;

import inheritance.annotations.Root;

/**
 * Интерфейс с дженериками для тестирования поддержки параметризованных типов
 * в системе множественного наследования
 * 
 * @param <T> Параметр типа, который будет использоваться в методах
 */
@Root
public interface GenericInterface<T> {
    /**
     * Возвращает значение типа T
     * @return Значение типа T
     */
    T getValue();
    
    /**
     * Устанавливает значение типа T
     * @param value Новое значение
     */
    void setValue(T value);
    
    /**
     * Трансформирует значение типа T в строку с добавлением префикса
     * @param prefix Префикс для добавления
     * @return Строковое представление значения с префиксом
     */
    String transformValue(String prefix);
} 