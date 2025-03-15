package example.diamond;

/**
 * Базовый класс A - основа для ромбовидного наследования
 */
public class A extends SomeInterfaceRoot {
    private int value = 0;

    public A() {
        System.out.println("A created");
        value++;
    }


    @Override
    public void method() {
        System.out.println(value);

        System.out.println("A.method(): Выполнение метода в базовом классе");
        nextMethod();
    }
    
    /**
     * Получает родительский объект
     * @return Родительский объект
     */
    public Object getParent() {
        return parent;
    }
} 