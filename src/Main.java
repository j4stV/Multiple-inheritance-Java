import example.MyPet;
import example.Dog;
import example.Animal;
import example.DualInheritance;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация множественного наследования с одним родителем ===");
        
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
        
        System.out.println("\n\n=== Демонстрация множественного наследования с двумя родителями ===");
        
        try {
            // Создаем экземпляр класса с двойным наследованием
            DualInheritance dualPet = new DualInheritance();
            
            System.out.println("Создан экземпляр DualInheritance");
            
            // Вызываем методы
            System.out.println("\nВызываем методы наследуемые от Animal (через Dog):");
            dualPet.makeSound();
            System.out.println("Тип: " + dualPet.getType());
            
            System.out.println("\nДемонстрация доступа к обоим родителям:");
            dualPet.showMultipleInheritance();
        } catch (Exception e) {
            System.out.println("Ошибка при создании DualInheritance: " + e.getMessage());
            e.printStackTrace();
        }
    }
}