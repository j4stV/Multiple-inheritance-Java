package inheritance.tests.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс C для ромбовидного наследования
 * Наследуется от класса A через аннотацию @Mixin
 */
@Mixin(ClassA.class)
public class ClassC extends DiamondInterfaceRoot {
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
    
    /**
     * Уникальный метод класса C
     * @return Строка, идентифицирующая класс C
     */
    public String methodC() {
        return "C-specific";
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
} 