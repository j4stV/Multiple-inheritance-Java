package example;

import inheritance.annotations.Mixin;

/**
 * Реализация интерфейса Pet
 */
@Mixin({MyPet.class})
public class Cat extends AnimalRoot {
    @Override
    public void makeSound() {
        System.out.println("I am cat");
        nextMakeSound();
        System.out.println("Still cat");
    }

    @Override
    public String getType() {
        return "Мурка";
    }
} 