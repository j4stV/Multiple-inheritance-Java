package inheritance.tests.constructor;

import inheritance.annotations.Root;

/**
 * Interface for testing constructors with arguments
 */
@Root
public interface ConstructorInterface {
    /**
     * Gets the value stored during construction
     * @return stored value
     */
    String getValue();
} 