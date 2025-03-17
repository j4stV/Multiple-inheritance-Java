package inheritance.tests.repeatedAncestor;

import inheritance.annotations.Mixin;

/**
 * Класс D для теста с повторяющимся предком
 * Наследуется от классов A и B через аннотацию @Mixin
 * При этом класс A является общим предком - как напрямую, так и через B
 */
@Mixin({ClassA.class, ClassB.class})
public class ClassD extends RepeatedAncestorInterfaceRoot {
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
     * Метод для проверки доступа к методам родительских классов
     * @return Комбинация результатов вызова родительских специфических методов
     */
    public String callParentSpecificMethods() {
        // Формируем результат вызовов
        StringBuilder result = new StringBuilder();
        
        // Проверяем доступ к методу класса A
        if (parent instanceof ClassA) {
            ClassA parentA = (ClassA) parent;
            result.append(parentA.methodA()).append("-");
        }
        
        // Проверяем доступ к методу класса B
        if (parent instanceof ClassB) {
            ClassB parentB = (ClassB) parent;
            result.append(parentB.methodB());
        }
        
        return result.toString();
    }
} 