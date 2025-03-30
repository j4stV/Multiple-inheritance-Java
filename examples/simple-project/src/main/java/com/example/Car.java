package com.example;

/**
 * Base car class
 */
public class Car extends VehicleRoot {
    private boolean engineRunning = false;
    protected int speed = 0;
    
    @Override
    public void startEngine() {
        System.out.println("Car.startEngine(): Starting car engine");
        engineRunning = true;
        nextStartEngine();
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Car.stopEngine(): Stopping car engine");
        engineRunning = false;
        nextStopEngine();
    }
    
    @Override
    public void move() {
        speed = 60;
        System.out.println("Car.move(): Moving at " + speed + " km/h");
        nextMove();
    }
    
    /**
     * Method specific to the car
     */
    public void honk() {
        System.out.println("Car: Honk! Honk!");
    }
} 