import example.diamond.*;
import inheritance.factory.MixinFactory;

/**
 * Main class for demonstrating the diamond inheritance mechanism
 */
public class Main {

    /**
     * Program entry point
     *
     * @param args command line arguments
     *             Supported arguments:
     *             --debug=true|false - enable/disable debug information
     */
    public static void main(String[] args) {
        System.out.println("=== Demonstration of multiple inheritance mechanism in Java ===");

        // Process command line arguments
        processArgs(args);
        MixinFactory.setDebugEnabled(true);
        try {
            // Run diamond inheritance test
            DiamondTest.runTest();

            // Create and test a separate instance of D
            System.out.println("\n=== Additional demonstration of class D ===");
            D d = SomeInterfaceRoot.createInstance(D.class);
            d.method();

        } catch (Exception e) {
            System.err.println("Error during execution: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== Demonstration completed ===");
    }

    /**
     * Process command line arguments
     *
     * @param args array of arguments
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
                System.out.println("Debug information " + (debug ? "enabled" : "disabled"));
            }
        }
    }
}