package com.example;

import inheritance.annotations.Mixin;

/**
 * Amphibious vehicle class with inheritance from Car and Boat
 */
@Mixin({Car.class, Boat.class})
public class Amphibian extends VehicleRoot {
    private boolean isInWaterMode = false;
    
    @Override
    public void startEngine() {
        System.out.println("Amphibian: Starting combined engine system");
        nextStartEngine();
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Amphibian: Stopping combined engine system");
        nextStopEngine();
    }
    
    @Override
    public void move() {
        System.out.println("Amphibian: Moving in " + (isInWaterMode ? "water" : "land") + " mode");
        nextMove(); // Calls parent class methods in topological sort order
    }
    
    /**
     * Method for switching mode (water/land)
     */
    public void switchMode() {
        isInWaterMode = !isInWaterMode;
        System.out.println("Amphibian: Switched to " + (isInWaterMode ? "water" : "land") + " mode");
    }
    
    /**
     * Method for accessing specific methods of parent classes
     */
    public void useSpecificFeatures() {
        // Check and call Car methods
        if (parent instanceof Car) {
            ((Car) parent).honk();
        } else if (parent instanceof Boat) {
            ((Boat) parent).sail();
        }
        
        // For a complete example with the chain Amphibian -> Car -> Boat
        // it might be necessary to get "parent of parent"
        if (parent != null && parent instanceof Car) {
            Car car = (Car) parent;
            if (car.parent instanceof Boat) {
                Boat boat = (Boat) car.parent;
                boat.sail();
            }
        }
    }
} 