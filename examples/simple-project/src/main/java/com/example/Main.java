package com.example;

import inheritance.factory.MixinFactory;

/**
 * Main class for multiple inheritance demonstration
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Java Multiple Inheritance Demo ===");

        MixinFactory.setDebugEnabled(true);

        System.out.println("\n=== Creating Amphibian instance ===");
        Amphibian amphibian = MixinFactory.createInstance(Amphibian.class);

        System.out.println("\n=== Starting engine ===");
        amphibian.startEngine();
        
        System.out.println("\n=== Moving in default mode ===");
        amphibian.move();
        
        System.out.println("\n=== Switching to water mode ===");
        amphibian.switchMode();
        
        System.out.println("\n=== Moving in water mode ===");
        amphibian.move();
        
        System.out.println("\n=== Using specific features ===");
        amphibian.useSpecificFeatures();
        
        System.out.println("\n=== Stopping engine ===");
        amphibian.stopEngine();

        System.out.println("\n=== Creating Car instance ===");
        Car car = MixinFactory.createInstance(Car.class);
        car.startEngine();
        car.move();
        car.honk();
        car.stopEngine();
        
        System.out.println("\n=== Creating Boat instance ===");
        Boat boat = MixinFactory.createInstance(Boat.class);
        boat.startEngine();
        boat.move();
        boat.sail();
        boat.stopEngine();
    }
} 