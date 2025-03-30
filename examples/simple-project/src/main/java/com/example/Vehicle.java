package com.example;

import inheritance.annotations.Root;

/**
 * Root interface for multiple inheritance demonstration
 */
@Root
public interface Vehicle {
    /**
     * Method to start the engine
     */
    void startEngine();
    
    /**
     * Method to stop the engine
     */
    void stopEngine();
    
    /**
     * Method for moving
     */
    void move();
} 