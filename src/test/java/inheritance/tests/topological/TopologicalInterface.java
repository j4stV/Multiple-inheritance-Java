package inheritance.tests.topological;

import inheritance.annotations.Root;

/**
 * Base interface for testing topological sorting
 */
@Root
public interface TopologicalInterface {
    /**
     * Method for checking call order in the inheritance chain
     * @return Class name and data about call order
     */
    String getTopologicalOrder();
} 