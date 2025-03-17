package inheritance.tests.cyclic;

import inheritance.annotations.Mixin;

/**
 * Класс B для циклического наследования
 * Создает циклическую структуру с классами A и C
 * Наследуется от класса A через аннотацию @Mixin
 */
@Mixin(ClassA.class)
public class ClassB extends CyclicInterfaceRoot {
    private int callCounter = 0;
    
    /**
     * Реализация метода из интерфейса
     * @return "B" + результат вызова метода из родительского класса
     */
    public String testMethod() {
        callCounter++;
        
        // Ограничиваем количество вызовов для предотвращения бесконечного цикла
        if (callCounter > 3) {
            return "B(stop)";
        }
        
        System.out.println("B.testMethod(): выполнение [" + callCounter + "]");
        return "B" + nextTestMethod();
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
    
    /**
     * Сбрасывает счетчик вызовов для повторных тестов
     */
    public void resetCallCounter() {
        callCounter = 0;
    }
} 