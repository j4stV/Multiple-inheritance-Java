package example.diamond;

import inheritance.annotations.Mixin;

/**
 * Класс E - третий класс в ромбовидной иерархии наследования
 */
@Mixin(A.class)
public class E extends SomeInterfaceRoot {

    @Override
    public void method() {
        System.out.println("E.method(): Начало выполнения");

        // Вызываем метод через цепочку наследования
        nextMethod();

        System.out.println("E.method(): Конец выполнения");
    }

    /**
     * Уникальный метод класса E
     */
    public void methodE() {
        System.out.println("E.methodE(): Выполнение уникального метода класса E");
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
}