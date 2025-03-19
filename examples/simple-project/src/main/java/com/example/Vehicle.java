package com.example;

import inheritance.annotations.Root;

/**
 * Корневой интерфейс для демонстрации множественного наследования
 */
@Root
public interface Vehicle {
    /**
     * Метод для запуска двигателя
     */
    void startEngine();
    
    /**
     * Метод для остановки двигателя
     */
    void stopEngine();
    
    /**
     * Метод для движения
     */
    void move();
} 