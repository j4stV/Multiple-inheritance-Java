package com.example;

/**
 * Boat class
 */
public class Boat extends VehicleRoot {
    private boolean engineRunning = false;
    
    @Override
    public void startEngine() {
        System.out.println("Boat: Starting engine");
        engineRunning = true;
        nextStartEngine(); // Call the next class method in the chain
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Boat: Stopping engine");
        engineRunning = false;
        nextStopEngine(); // Call the next class method in the chain
    }
    
    @Override
    public void move() {
        if (engineRunning) {
            System.out.println("Boat: Sailing on water");
        } else {
            System.out.println("Boat: Cannot sail, engine is off");
        }
        nextMove(); // Call the next class method in the chain
    }
    
    /**
     * Method specific to the boat
     */
    public void sail() {
        System.out.println("Boat: Setting sails");
    }
} 