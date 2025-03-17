package inheritance.tests.repeatedAncestor;

import inheritance.annotations.Mixin;

/**
 * Класс B для теста с повторяющимся предком
 * Наследуется от класса A через аннотацию @Mixin
 */
@Mixin(ClassA.class)
public class ClassB extends RepeatedAncestorInterfaceRoot {
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
     * Метод, специфичный для класса B
     * @return Строка, указывающая на выполнение метода в классе B
     */
    public String methodB() {
        return "B-specific";
    }
} 