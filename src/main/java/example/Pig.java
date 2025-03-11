package example;

import inheritance.annotations.Mixin;

/**
 * Пример класса-родителя
 */
@Mixin({MyPet.class})
public class Pig extends AnimalRoot {
    @Override
    public void makeSound() {
        System.out.println("I am pig");
        nextMakeSound();
    }

    @Override
    public String getType() {
        return "Dog";
    }
}