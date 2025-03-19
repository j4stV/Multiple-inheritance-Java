package com.example;

import inheritance.annotations.Mixin;

/**
 * Класс транспортного средства-амфибии с наследованием от Car и Boat
 */
@Mixin({Car.class, Boat.class})
public class Amphibian extends VehicleRoot {
    private boolean isInWaterMode = false;
    
    @Override
    public void startEngine() {
        System.out.println("Amphibian: Starting combined engine system");
        nextStartEngine(); // Вызывает методы родительских классов в порядке топологической сортировки
    }
    
    @Override
    public void stopEngine() {
        System.out.println("Amphibian: Stopping combined engine system");
        nextStopEngine(); // Вызывает методы родительских классов в порядке топологической сортировки
    }
    
    @Override
    public void move() {
        System.out.println("Amphibian: Moving in " + (isInWaterMode ? "water" : "land") + " mode");
        nextMove(); // Вызывает методы родительских классов в порядке топологической сортировки
    }
    
    /**
     * Метод для переключения режима (вода/суша)
     */
    public void switchMode() {
        isInWaterMode = !isInWaterMode;
        System.out.println("Amphibian: Switched to " + (isInWaterMode ? "water" : "land") + " mode");
    }
    
    /**
     * Метод для доступа к специфичным методам родительских классов
     */
    public void useSpecificFeatures() {
        // Проверяем и вызываем методы Car
        if (parent instanceof Car) {
            ((Car) parent).honk();
        } else if (parent instanceof Boat) {
            ((Boat) parent).sail();
        }
        
        // Для полного примера с цепочкой Amphibian -> Car -> Boat
        // могло бы понадобиться получить "parent of parent"
        if (parent != null && parent instanceof Car) {
            Car car = (Car) parent;
            if (car.parent instanceof Boat) {
                Boat boat = (Boat) car.parent;
                boat.sail();
            }
        }
    }
} 