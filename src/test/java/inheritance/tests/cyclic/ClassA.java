package inheritance.tests.cyclic;

import inheritance.annotations.Mixin;

/**
 * Класс A для циклического наследования
 * Создает циклическую структуру с классами B и C
 * Наследуется от класса C через аннотацию @Mixin
 */
@Mixin(ClassC.class)
public class ClassA extends CyclicInterfaceRoot {
    private int callCounter = 0;
    
    /**
     * Реализация метода из интерфейса
     * @return "A" + результат вызова метода из родительского класса
     */
    public String testMethod() {
        callCounter++;
        
        // Ограничиваем количество вызовов для предотвращения бесконечного цикла
        if (callCounter > 3) {
            return "A(stop)";
        }
        
        System.out.println("A.testMethod(): выполнение [" + callCounter + "]");
        return "A" + nextTestMethod();
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