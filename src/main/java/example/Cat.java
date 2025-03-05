package example;

/**
 * Реализация интерфейса Pet
 */
public class Cat implements Pet {
    @Override
    public void play() {
        System.out.println("Кошка играет с клубком.");
    }

    @Override
    public String getName() {
        return "Мурка";
    }
} 