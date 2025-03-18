package inheritance.tests.cyclic;

import inheritance.annotations.Root;

/**
 * Interface for testing cyclic inheritance (A -> B -> C -> A)
 */
@Root
public interface CyclicInterface {
    /**
     * Test method that will be used in cyclic inheritance classes
     * @return String reflecting the hierarchy of method calls
     */
    String testMethod();
} 