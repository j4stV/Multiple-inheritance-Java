package inheritance.tests.repeatedAncestor;

/**
 * Базовый класс A для теста с повторяющимся предком
 * Реализует интерфейс RepeatedAncestorInterface и служит началом цепочки наследования
 */
public class ClassA extends RepeatedAncestorInterfaceRoot {
    /**
     * Реализация метода из интерфейса
     * @return "A" - идентификатор класса
     */
    public String testMethod() {
        System.out.println("A.testMethod(): выполнение");
        return "A";
    }
    
    /**
     * Метод, специфичный для класса A
     * @return Строка, указывающая на выполнение метода в классе A
     */
    public String methodA() {
        return "A-specific";
    }
} 