package inheritance.tests.topological;

import inheritance.annotations.Root;

/**
 * Базовый интерфейс для тестирования топологической сортировки
 */
@Root
public interface TopologicalInterface {
    /**
     * Метод для проверки порядка вызовов в цепочке наследования
     * @return Имя класса и данные о порядке вызова
     */
    String getTopologicalOrder();
} 