package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс B - первый класс в ромбовидной иерархии наследования
 */
@Mixin(A.class)
public class B extends SomeInterfaceRoot {
    
    @Override
    public void method() {
        System.out.println("B.method(): Начало выполнения");
        
        // Вызываем метод через цепочку наследования
        nextMethod();
        
        System.out.println("B.method(): Конец выполнения");
    }
    
    /**
     * Уникальный метод класса B
     */
    public void methodB() {
        System.out.println("B.methodB(): Выполнение уникального метода класса B");
    }
} 