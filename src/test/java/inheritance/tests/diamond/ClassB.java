package inheritance.tests.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс B для ромбовидного наследования
 * Наследуется от класса A через аннотацию @Mixin
 */
@Mixin(ClassA.class)
public class ClassB extends DiamondInterfaceRoot {
    /**
     * Реализация метода из интерфейса
     * @return "B" + результат вызова метода из родительского класса
     */
    public String testMethod() {
        System.out.println("B.testMethod(): начало выполнения");
        String result = "B" + nextTestMethod();
        System.out.println("B.testMethod(): конец выполнения");
        return result;
    }
    
    /**
     * Уникальный метод класса B
     * @return Строка, идентифицирующая класс B
     */
    public String methodB() {
        return "B-specific";
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
} 