package inheritance.tests.generic;

import inheritance.annotations.Root;

/**
 * Interface with generics for testing support of parameterized types
 * in the multiple inheritance system
 * 
 * @param <T> Type parameter that will be used in methods
 */
@Root
public interface GenericInterface<T> {
    /**
     * Returns a value of type T
     * @return Value of type T
     */
    T getValue();
    
    /**
     * Sets a value of type T
     * @param value New value
     */
    void setValue(T value);
    
    /**
     * Transforms value of type T into a string with a prefix
     * @param prefix Prefix to add
     * @return String representation of the value with a prefix
     */
    String transformValue(String prefix);
} 