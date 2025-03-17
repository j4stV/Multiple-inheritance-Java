package inheritance.tests.repeatedAncestor;

import inheritance.annotations.Root;

/**
 * Корневой интерфейс для теста с повторяющимся предком
 * Помечен аннотацией @Root для генерации базового класса
 */
@Root
public interface RepeatedAncestorInterface {
    /**
     * Тестовый метод для проверки цепочки наследования
     * @return Строка, представляющая порядок выполнения метода через цепочку наследования
     */
    String testMethod();
} 