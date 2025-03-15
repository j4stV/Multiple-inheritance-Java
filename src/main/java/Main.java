import example.diamond.*;

/**
 * Главный класс для демонстрации работы механизма ромбовидного наследования
 */
public class Main {
    
    /**
     * Точка входа в программу
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        System.out.println("=== Демонстрация механизма множественного наследования в Java ===");
        
        try {
            // Запускаем тест ромбовидного наследования
            DiamondTest.runTest();
            
            // Создаем и тестируем отдельный экземпляр D
            System.out.println("\n=== Дополнительная демонстрация работы класса D ===");
            D d = SomeInterfaceRoot.createInstance(D.class);
            d.method();
            
        } catch (Exception e) {
            System.err.println("Ошибка во время выполнения: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Демонстрация завершена ===");
    }
}