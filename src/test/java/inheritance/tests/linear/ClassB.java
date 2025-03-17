package inheritance.tests.linear;

import inheritance.annotations.Mixin;

/**
 * Класс B для линейного наследования
 * Наследуется от класса A через аннотацию @Mixin
 */
@Mixin(ClassA.class)
public class ClassB extends TestLinearInterfaceRoot {
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
} 