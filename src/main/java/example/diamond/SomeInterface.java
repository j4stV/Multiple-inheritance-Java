package example.diamond;

import inheritance.annotations.Root;

/**
 * Root interface with @Root mark for base class generation
 */
@Root
public interface SomeInterface {
    
    /**
     * Main interface method that will be implemented in all classes
     */
    void method();
} 