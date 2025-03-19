package com.example;

/**
 * Базовый класс автомобиля
 */
public class Car extends VehicleRoot {
    private boolean engineRunning = false;
    
    @Override
    public void startEngine() {
        System.out.println("Car: Starting engine");
        engineRunning = true;
        nextStartEngine(); // Вызывает метод следующего класса в цепочке
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Car: Stopping engine");
        engineRunning = false;
        nextStopEngine(); // Вызывает метод следующего класса в цепочке
    }
    
    @Override
    public void move() {
        if (engineRunning) {
            System.out.println("Car: Moving on road");
        } else {
            System.out.println("Car: Cannot move, engine is off");
        }
        nextMove(); // Вызывает метод следующего класса в цепочке
    }
    
    /**
     * Метод, специфичный для автомобиля
     */
    public void honk() {
        System.out.println("Car: Honk! Honk!");
    }
} 