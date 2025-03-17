package inheritance.tests.linear;

import inheritance.annotations.Mixin;

/**
 * Класс C для линейного наследования
 * Наследуется от класса B через аннотацию @Mixin
 */
@Mixin(ClassB.class)
public class ClassC extends TestLinearInterfaceRoot {
    /**
     * Реализация метода из интерфейса
     * @return "C" + результат вызова метода из родительского класса
     */
    public String testMethod() {
        System.out.println("C.testMethod(): начало выполнения");
        String result = "C" + nextTestMethod();
        System.out.println("C.testMethod(): конец выполнения");
        return result;
    }
} 