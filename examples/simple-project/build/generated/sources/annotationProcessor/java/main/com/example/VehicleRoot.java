package com.example;

import inheritance.annotations.Mixin;
import inheritance.factory.MixinFactory;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

/**
 * Automatically generated root class for interface Vehicle
 */
public abstract class VehicleRoot implements Vehicle {

    protected Object parent;

    private static final int MAX_CALL_DEPTH = 20;
    private static final ThreadLocal<Integer> callDepth = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public VehicleRoot() {
        // Initialization is performed by MixinFactory
    }

    /**
     * Creates an instance of the class through MixinFactory
     * @param clazz Class to create an instance of
     * @param <T> Type of instance being created
     * @return Configured instance of the class
     */
    public static <T> T createInstance(Class<T> clazz) {
        return MixinFactory.createInstance(clazz);
    }

    protected void nextStartEngine() {
        if (parent == null) {
            return;
        }

        int currentDepth = callDepth.get();
        if (currentDepth >= MAX_CALL_DEPTH) {
            System.out.println("Maximum method call depth exceeded: " + MAX_CALL_DEPTH + ". Possible cycle detected.");
            return;
        }
        callDepth.set(currentDepth + 1);

        try {
            Method method = parent.getClass().getMethod("startEngine");
            method.invoke(parent);
            callDepth.set(currentDepth); // Restore counter
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            callDepth.set(currentDepth); // Restore counter in case of error
            throw new RuntimeException("Error calling parent class method", e);
        }
    }

    protected void nextStopEngine() {
        if (parent == null) {
            return;
        }

        int currentDepth = callDepth.get();
        if (currentDepth >= MAX_CALL_DEPTH) {
            System.out.println("Maximum method call depth exceeded: " + MAX_CALL_DEPTH + ". Possible cycle detected.");
            return;
        }
        callDepth.set(currentDepth + 1);

        try {
            Method method = parent.getClass().getMethod("stopEngine");
            method.invoke(parent);
            callDepth.set(currentDepth); // Restore counter
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            callDepth.set(currentDepth); // Restore counter in case of error
            throw new RuntimeException("Error calling parent class method", e);
        }
    }

    protected void nextMove() {
        if (parent == null) {
            return;
        }

        int currentDepth = callDepth.get();
        if (currentDepth >= MAX_CALL_DEPTH) {
            System.out.println("Maximum method call depth exceeded: " + MAX_CALL_DEPTH + ". Possible cycle detected.");
            return;
        }
        callDepth.set(currentDepth + 1);

        try {
            Method method = parent.getClass().getMethod("move");
            method.invoke(parent);
            callDepth.set(currentDepth); // Restore counter
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            callDepth.set(currentDepth); // Restore counter in case of error
            throw new RuntimeException("Error calling parent class method", e);
        }
    }

}
