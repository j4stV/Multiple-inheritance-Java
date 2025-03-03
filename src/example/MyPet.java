package example;

import inheritance.annotations.Mixin;

/**
 * Пример класса с использованием множественного наследования (один родитель)
 */
@Mixin(Dog.class)
public class MyPet extends AnimalRoot {
    @Override
    public void makeSound() {
        System.out.println("Мой питомец говорит: ");
        nextMakeSound(); // Вызываем метод родителя
        System.out.println("И хочет гулять!");
    }

    @Override
    public String getType() {
        String parentType = nextGetType(); // Получаем тип от родителя
        return "Мой любимый " + parentType;
    }
} 