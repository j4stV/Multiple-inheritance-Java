package inheritance.tests.diamond;

import inheritance.annotations.Root;

/**
 * Interface for testing diamond inheritance (A -> B,C -> D)
 */
@Root
public interface DiamondInterface {
    /**
     * Test method that will be used in diamond inheritance classes
     * @return String reflecting the hierarchy of method calls
     */
    String testMethod();
} 