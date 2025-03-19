package com.example;

/**
 * Класс лодки
 */
public class Boat extends VehicleRoot {
    private boolean engineRunning = false;
    
    @Override
    public void startEngine() {
        System.out.println("Boat: Starting engine");
        engineRunning = true;
        nextStartEngine(); // Вызывает метод следующего класса в цепочке
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Boat: Stopping engine");
        engineRunning = false;
        nextStopEngine(); // Вызывает метод следующего класса в цепочке
    }
    
    @Override
    public void move() {
        if (engineRunning) {
            System.out.println("Boat: Sailing on water");
        } else {
            System.out.println("Boat: Cannot sail, engine is off");
        }
        nextMove(); // Вызывает метод следующего класса в цепочке
    }
    
    /**
     * Метод, специфичный для лодки
     */
    public void sail() {
        System.out.println("Boat: Setting sails");
    }
} 