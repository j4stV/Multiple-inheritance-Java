package example;

import inheritance.annotations.Mixin;

/**
 * Пример класса с двойным наследованием, наследующий как от Dog, так и от Cat
 */
@Mixin({Dog.class, Cat.class, Pig.class})
public class DualInheritance extends AnimalRoot {
    @Override
    public void makeSound() {
        System.out.println("Существо с двойным наследованием:");
        nextMakeSound(); // Вызывает метод Dog, который является первым в цепочке

    }

    @Override
    public String getType() {
        return "Гибрид: " + nextGetType();
    }

    /**
     * Метод для демонстрации доступа к методам второго родителя (Cat)
     */
    public void showMultipleInheritance() {
        System.out.println("Демонстрация множественного наследования:");
        makeSound(); // От Dog

        // Доступ к первому родителю - Dog через поле parent
        System.out.println("\nРодитель 1 (Dog) - тип: " + ((Animal) parent).getType());

        // Доступ ко второму родителю - Cat через метод getMixinParent
        Object cat = getMixinParent(1);
        if (cat instanceof Pet) {
            System.out.println("\nРодитель 2 (Cat) - имя: " + ((Pet) cat).getName());
            System.out.println("Родитель 2 (Cat) играет:");
            ((Pet) cat).play();
        }

        // Выводим количество родителей
        System.out.println("\nВсего родителей: " + getMixinParentsCount());
    }
} 