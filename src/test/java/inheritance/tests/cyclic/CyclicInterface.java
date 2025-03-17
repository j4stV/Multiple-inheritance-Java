package inheritance.tests.cyclic;

import inheritance.annotations.Root;

/**
 * Интерфейс для тестирования циклического наследования (A -> B -> C -> A)
 */
@Root
public interface CyclicInterface {
    /**
     * Тестовый метод, который будет использоваться в классах циклического наследования
     * @return Строку, отражающую иерархию вызовов методов
     */
    String testMethod();
} 