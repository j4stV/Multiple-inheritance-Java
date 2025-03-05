package example;

import inheritance.annotations.Mixin;

/**
 * Пример класса, использующего PetRoot с Cat в качестве миксина
 */
@Mixin(Cat.class)
public class Playful extends PetRoot {
    @Override
    public void play() {
        System.out.println("Игривое животное начинает играть:");
        nextPlay(); // Вызов метода родителя (Cat)
        System.out.println("И продолжает играть очень активно!");
    }

    @Override
    public String getName() {
        return "Игривая " + nextGetName();
    }
} 