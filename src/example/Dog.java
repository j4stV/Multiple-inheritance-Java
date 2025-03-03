package example;

/**
 * Пример класса-родителя
 */
public class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Гав-гав!");
    }

    @Override
    public String getType() {
        return "Собака";
    }
} 