package inheritance.tests.diamond;

import inheritance.annotations.Root;

/**
 * Интерфейс для тестирования ромбовидного наследования (A -> B,C -> D)
 */
@Root
public interface DiamondInterface {
    /**
     * Тестовый метод, который будет использоваться в классах ромбовидного наследования
     * @return Строку, отражающую иерархию вызовов методов
     */
    String testMethod();
} 