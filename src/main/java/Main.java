import example.diamond.*;
import inheritance.factory.MixinFactory;

/**
 * Главный класс для демонстрации работы механизма ромбовидного наследования
 */
public class Main {

    /**
     * Точка входа в программу
     *
     * @param args аргументы командной строки
     *             Поддерживаемые аргументы:
     *             --debug=true|false - включение/отключение отладочной информации
     */
    public static void main(String[] args) {
        System.out.println("=== Демонстрация механизма множественного наследования в Java ===");

        // Обработка аргументов командной строки
        processArgs(args);
        MixinFactory.setDebugEnabled(true);
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

    /**
     * Обрабатывает аргументы командной строки
     *
     * @param args массив аргументов
     */
    private static void processArgs(String[] args) {
        if (args == null || args.length == 0) {
            return;
        }

        for (String arg : args) {
            if (arg.startsWith("--debug=")) {
                String value = arg.substring("--debug=".length());
                boolean debug = Boolean.parseBoolean(value);
                MixinFactory.setDebugEnabled(debug);
                System.out.println("Отладочная информация " + (debug ? "включена" : "отключена"));
            }
        }
    }
}