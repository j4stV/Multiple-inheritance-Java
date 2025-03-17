package inheritance.tests.cyclic;

import inheritance.annotations.Mixin;

/**
 * Класс C для циклического наследования
 * Создает циклическую структуру с классами A и B
 * Наследуется от класса B через аннотацию @Mixin
 */
@Mixin(ClassB.class)
public class ClassC extends CyclicInterfaceRoot {
    private int callCounter = 0;
    
    /**
     * Реализация метода из интерфейса
     * @return "C" + результат вызова метода из родительского класса
     */
    public String testMethod() {
        callCounter++;
        
        // Ограничиваем количество вызовов для предотвращения бесконечного цикла
        if (callCounter > 3) {
            return "C(stop)";
        }
        
        System.out.println("C.testMethod(): выполнение [" + callCounter + "]");
        return "C" + nextTestMethod();
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