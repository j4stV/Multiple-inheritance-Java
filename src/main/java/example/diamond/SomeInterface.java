package example.diamond;

import inheritance.annotations.Root;

/**
 * Корневой интерфейс с меткой @Root для генерации базового класса
 */
@Root
public interface SomeInterface {
    
    /**
     * Основной метод интерфейса, который будет реализован во всех классах
     */
    void method();
} 