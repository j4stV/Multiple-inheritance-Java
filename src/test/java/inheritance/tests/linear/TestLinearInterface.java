package inheritance.tests.linear;

import inheritance.annotations.Root;

/**
 * Interface for testing linear inheritance (A -> B -> C)
 */
@Root
public interface TestLinearInterface {
    /**
     * Test method that will be used in inheritance classes
     * @return String reflecting the hierarchy of method calls
     */
    String testMethod();
} 