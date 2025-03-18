package inheritance.tests.repeatedAncestor;

import inheritance.annotations.Root;

/**
 * Root interface for test with repeated ancestor
 * Marked with @Root annotation for base class generation
 */
@Root
public interface RepeatedAncestorInterface {
    /**
     * Test method for checking inheritance chain
     * @return String representing the order of method execution through inheritance chain
     */
    String testMethod();
} 