package inheritance.tests.linear;

import inheritance.annotations.Root;

/**
 * Интерфейс для тестирования линейного наследования (A -> B -> C)
 */
@Root
public interface TestLinearInterface {
    /**
     * Тестовый метод, который будет использоваться в классах наследования
     * @return Строку, отражающую иерархию вызовов методов
     */
    String testMethod();
} 