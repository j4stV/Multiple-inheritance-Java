import example.MyPet;
import example.Dog;
import example.Animal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Демонстрация множественного наследования с одним родителем");
        
        // Создаем экземпляр класса, использующего механизм множественного наследования
        MyPet pet = new MyPet();
        
        System.out.println("Создан экземпляр MyPet");
        
        // Проверяем, что pet является экземпляром Animal
        System.out.println("pet instanceof Animal: " + (pet instanceof Animal));
        
        // Проверяем, что аннотация Mixin присутствует
        System.out.println("Аннотация Mixin присутствует: " + (pet.getClass().getAnnotation(inheritance.annotations.Mixin.class) != null));
        
        // Вызываем методы
        System.out.println("\nВызываем метод makeSound():");
        pet.makeSound();
        
        System.out.println("\nВызываем метод getType():");
        System.out.println("Тип: " + pet.getType());
    }
}