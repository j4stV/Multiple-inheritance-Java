package inheritance.tests.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс D для ромбовидного наследования
 * Наследуется от классов B и C через аннотацию @Mixin
 */
@Mixin({ClassB.class, ClassC.class})
public class ClassD extends DiamondInterfaceRoot {
    /**
     * Реализация метода из интерфейса
     * @return "D" + результат вызова метода из родительского класса
     */
    public String testMethod() {
        System.out.println("D.testMethod(): начало выполнения");
        String result = "D" + nextTestMethod();
        System.out.println("D.testMethod(): конец выполнения");
        return result;
    }
    
    /**
     * Метод для проверки доступа к уникальным методам родительских классов
     * @return Результат вызова специфического метода родительского класса B
     */
    public String callParentSpecificMethods() {
        if (parent != null && parent instanceof ClassB) {
            ClassB parentB = (ClassB) parent;
            return parentB.methodB();
        }
        return "";
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
} 