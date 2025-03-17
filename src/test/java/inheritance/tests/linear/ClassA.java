package inheritance.tests.linear;

/**
 * Базовый класс A для линейного наследования
 * Реализует интерфейс TestLinearInterface и служит началом цепочки наследования
 */
public class ClassA extends TestLinearInterfaceRoot {
    /**
     * Реализация метода из интерфейса
     * @return "A" - идентификатор класса
     */
    public String testMethod() {
        System.out.println("A.testMethod(): выполнение");
        return "A";
    }
} 