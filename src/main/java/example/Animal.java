package example;

import inheritance.annotations.Root;

/**
 * Пример корневого интерфейса с аннотацией @Root
 */
@Root
public interface Animal {
    void makeSound();
    String getType();
} 