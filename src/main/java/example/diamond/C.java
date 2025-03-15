package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс C - второй класс в ромбовидной иерархии наследования
 */
@Mixin(A.class)
public class C extends SomeInterfaceRoot {
    
    @Override
    public void method() {
        System.out.println("C.method(): Начало выполнения");
        
        // Вызываем метод через цепочку наследования
        nextMethod();
        
        System.out.println("C.method(): Конец выполнения");
    }
    
    /**
     * Уникальный метод класса C
     */
    public void methodC() {
        System.out.println("C.methodC(): Выполнение уникального метода класса C");
    }
} 