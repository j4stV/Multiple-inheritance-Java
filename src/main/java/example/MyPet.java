package example;

import inheritance.annotations.Mixin;

/**
 * Пример класса с использованием множественного наследования (один родитель)
 */
public class MyPet extends AnimalRoot {
    @Override
    public void makeSound() {
        System.out.println("I am myPet ");
    }

    @Override
    public String getType() {
        return "My pet";
    }
} 