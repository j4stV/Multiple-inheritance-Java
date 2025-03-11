package example;

import inheritance.annotations.Mixin;

/**
 * Пример класса-родителя
 */
@Mixin({MyPet.class})
public class Dog extends AnimalRoot {
    @Override
    public void makeSound() {
        System.out.println("I am dog");
        nextMakeSound();
    }

    @Override
    public String getType() {
        return "Dog";
    }
} 