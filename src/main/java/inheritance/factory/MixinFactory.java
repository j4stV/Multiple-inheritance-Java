package inheritance.factory;

import inheritance.annotations.Mixin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Factory for creating instances of classes with multiple inheritance.
 * Uses a topological sorting algorithm to determine the order of
 * instance creation and establishing connections between them.
 */
public class MixinFactory {
    
    private static final Map<Class<?>, Object> instanceCache = new HashMap<>();
    private static boolean debugEnabled = true;
    
    /**
     * Enables or disables debug output
     * @param enabled true to enable, false to disable
     */
    public static void setDebugEnabled(boolean enabled) {
        debugEnabled = enabled;
    }
    
    /**
     * Creates an instance of a class with multiple inheritance
     * 
     * @param clazz Class to instantiate
     * @param <T> Type of instance to create
     * @return Configured class instance
     */
    public static <T> T createInstance(Class<T> clazz) {
        if (debugEnabled) {
            System.out.println("\n=== Creating instance of class with multiple inheritance ===");
            System.out.println("Target class: " + clazz.getName());
        }
        
        instanceCache.clear();
        
        Map<Class<?>, List<Class<?>>> inheritanceGraph = buildInheritanceGraph(clazz);
        
        if (debugEnabled) {
            System.out.println("\n=== Inheritance graph ===");
            for (Map.Entry<Class<?>, List<Class<?>>> entry : inheritanceGraph.entrySet()) {
                System.out.println(entry.getKey().getSimpleName() + " inherits from: " + 
                    entry.getValue().stream()
                        .map(Class::getSimpleName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("<no parents>"));
            }
        }
        
        List<Class<?>> sortedClasses = topologicalSort(inheritanceGraph);
        
        if (debugEnabled) {
            System.out.println("\n=== Topological sort result ===");
            System.out.println("Order of class creation and linking (from initial to final):");
            for (int i = 0; i < sortedClasses.size(); i++) {
                System.out.println((i + 1) + ". " + sortedClasses.get(i).getSimpleName());
            }
        }
        
        createInstances(sortedClasses);
        
        setupParentRelationships(sortedClasses);
        
        return (T) instanceCache.get(clazz);
    }
    
    /**
     * Builds an inheritance graph starting from the specified class.
     * For each class, determines the list of its parents from the @Mixin annotation.
     * 
     * @param startClass Initial class for building the graph
     * @return Inheritance graph where key is the class, value is a list of its parents
     */
    private static Map<Class<?>, List<Class<?>>> buildInheritanceGraph(Class<?> startClass) {
        Map<Class<?>, List<Class<?>>> graph = new HashMap<>();
        Queue<Class<?>> queue = new LinkedList<>();
        Set<Class<?>> visited = new HashSet<>();
        
        queue.add(startClass);
        visited.add(startClass);
        
        while (!queue.isEmpty()) {
            Class<?> currentClass = queue.poll();
            
            if (currentClass.isInterface() || Modifier.isAbstract(currentClass.getModifiers())) {
                continue;
            }
            
            List<Class<?>> parents = getMixinClasses(currentClass);
            
            graph.put(currentClass, parents);
            
            for (Class<?> parent : parents) {
                if (!visited.contains(parent) && !parent.isInterface() && !Modifier.isAbstract(parent.getModifiers())) {
                    queue.add(parent);
                    visited.add(parent);
                }
            }
        }
        
        return graph;
    }
    
    /**
     * Performs topological sorting of the inheritance graph.
     * Ensures that parents will be placed in the list after their descendants.
     * 
     * @param graph Inheritance graph
     * @return Sorted list of classes
     */
    private static List<Class<?>> topologicalSort(Map<Class<?>, List<Class<?>>> graph) {
        List<Class<?>> result = new ArrayList<>();
        Set<Class<?>> visited = new HashSet<>();
        Set<Class<?>> processing = new HashSet<>();
        
        for (Class<?> clazz : graph.keySet()) {
            if (!visited.contains(clazz)) {
                dfs(clazz, graph, visited, processing, result);
            }
        }
        
        return result;
    }
    
    /**
     * Helper method for depth-first search during topological sorting.
     */
    private static void dfs(Class<?> current, Map<Class<?>, List<Class<?>>> graph, Set<Class<?>> visited, 
                            Set<Class<?>> processing, List<Class<?>> result) {
        if (processing.contains(current)) {
            throw new RuntimeException("Cyclic dependency detected in inheritance: " + current.getName());
        }
        
        if (visited.contains(current)) {
            return;
        }
        
        processing.add(current);
        
        List<Class<?>> parents = graph.getOrDefault(current, Collections.emptyList());
        for (Class<?> parent : parents) {
            if (!parent.isInterface() && !Modifier.isAbstract(parent.getModifiers()) && !visited.contains(parent)) {
                dfs(parent, graph, visited, processing, result);
            }
        }
        
        visited.add(current);
        processing.remove(current);
        result.add(current);
    }
    
    /**
     * Creates instances of all classes in topological sort order.
     * 
     * @param sortedClasses Sorted list of classes
     */
    private static void createInstances(List<Class<?>> sortedClasses) {
        for (Class<?> clazz : sortedClasses) {
            try {
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object instance = constructor.newInstance();
                
                instanceCache.put(clazz, instance);
                
                if (debugEnabled) {
                    System.out.println("Created instance of class: " + clazz.getSimpleName());
                }
                
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Error creating instance of class: " + clazz.getName(), e);
            }
        }
    }
    
    /**
     * Sets up parent relationships between created instances according to the result of
     * topological sorting, creating a chain of instances in the order they were created.
     * 
     * @param sortedClasses Sorted list of classes
     */
    private static void setupParentRelationships(List<Class<?>> sortedClasses) {
        if (debugEnabled) {
            System.out.println("\n=== Final inheritance order ===");
            System.out.println("(Classes are linked in topological sort order)");
        }
        
        if (sortedClasses.size() <= 1) {
            return;
        }
        
        for (int i = sortedClasses.size() - 1; i > 0; i--) {
            Class<?> currentClass = sortedClasses.get(i);
            Class<?> parentClass = sortedClasses.get(i - 1);
            
            Object currentInstance = instanceCache.get(currentClass);
            Object parentInstance = instanceCache.get(parentClass);
            
            if (currentInstance == null || parentInstance == null) {
                continue;
            }
            
            try {
                Field parentField = findField(currentClass, "parent");
                if (parentField != null) {
                    parentField.setAccessible(true);
                    parentField.set(currentInstance, parentInstance);
                    
                    if (debugEnabled) {
                        System.out.println("Established connection: " + currentClass.getSimpleName() + 
                                " -> " + parentClass.getSimpleName());
                    }
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Error setting parent field for " + currentClass.getName(), e);
            }
        }
        
        if (debugEnabled) {
            System.out.println("\n=== Inheritance structure built successfully ===");
        }
    }
    
    /**
     * Finds a field with the given name, including fields in parent classes
     * 
     * @param clazz Class to search
     * @param fieldName Field name
     * @return Found field or null
     */
    private static Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
    
    /**
     * Returns a list of classes specified in @Mixin annotations
     * 
     * @param clazz Class to analyze
     * @return List of mixin classes
     */
    private static List<Class<?>> getMixinClasses(Class<?> clazz) {
        List<Class<?>> mixinClasses = new ArrayList<>();
        Mixin[] mixins = clazz.getAnnotationsByType(Mixin.class);
        
        if (mixins != null) {
            for (Mixin mixin : mixins) {
                for (Class<?> mixinClass : mixin.value()) {
                    mixinClasses.add(mixinClass);
                }
            }
        }
        
        return mixinClasses;
    }
    
    /**
     * Clears the instance cache.
     */
    public static void clearCache() {
        instanceCache.clear();
    }
} 