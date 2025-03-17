package inheritance.tests.diamond;

/**
 * Базовый класс A для ромбовидного наследования
 * Служит началом иерархии наследования
 */
public class ClassA extends DiamondInterfaceRoot {
    /**
     * Реализация метода из интерфейса
     * @return "A" - идентификатор класса
     */
    public String testMethod() {
        System.out.println("A.testMethod(): выполнение");
        return "A";
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
} 