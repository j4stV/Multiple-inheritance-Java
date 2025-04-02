## Description

This library provides an implementation of multiple inheritance mechanism for Java using annotations, annotation processor, and a factory for creating class instances. The library allows overcoming Java's single inheritance limitation while maintaining type safety and predictable behavior.

## Library Components

1. **Annotations**:
   - `@Root` - marks an interface as the root in the inheritance hierarchy
   - `@Mixin` - specifies parent classes from which inheritance occurs

2. **Annotation Processor**:
   - `RootProcessor` - generates a base class for an interface marked with the `@Root` annotation

3. **Factory**:
   - `MixinFactory` - creates instances of classes with correctly configured inheritance chain

## Framework Integration into Project

### Gradle KTS

```kotlin
// Adding Maven repository (if publishing to Maven Central)
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

// Adding dependency
dependencies {
    implementation("com.java.multiple.inheritance:java-multiple-inheritance:1.0.0")
    annotationProcessor("com.java.multiple.inheritance:java-multiple-inheritance:1.0.0")
}
```

### Gradle Groovy

```groovy
// Adding Maven repository (if publishing to Maven Central)
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

// Adding dependency
dependencies {
    implementation 'com.java.multiple.inheritance:java-multiple-inheritance:1.0.1'
    annotationProcessor 'com.java.multiple.inheritance:java-multiple-inheritance:1.0.1'
}
```

### Maven

```xml
<dependency>
    <groupId>com.java.multiple.inheritance</groupId>
    <artifactId>java-multiple-inheritance</artifactId>
    <version>1.0.1</version>
</dependency>
```


### 2. Creating a Root Interface

Define a root interface and mark it with the `@Root` annotation:

```java
package example;

import inheritance.annotations.Root;

@Root
public interface SomeInterface {
    void method();
}
```

After compilation, the annotation processor will automatically generate a `SomeInterfaceRoot` class.

### 3. Creating Classes in the Inheritance Hierarchy

Create a base class that extends the generated class:

```java
package example;

public class A extends SomeInterfaceRoot {
    @Override
    public void method() {
        System.out.println("A.method(): Executing method in base class");
        nextMethod(); // Call the next method in the chain
    }
}
```

Then create classes inheriting from the base class, specifying parents via the `@Mixin` annotation:

```java
package example;

import inheritance.annotations.Mixin;

@Mixin(A.class)
public class B extends SomeInterfaceRoot {
    @Override
    public void method() {
        System.out.println("B.method(): Beginning execution");
        nextMethod(); // Call the next method in the chain
        System.out.println("B.method(): End of execution");
    }
    
    public void methodB() {
        System.out.println("B.methodB(): Unique method of class B");
    }
}
```

```java
package example;

import inheritance.annotations.Mixin;

@Mixin(A.class)
public class C extends SomeInterfaceRoot {
    @Override
    public void method() {
        System.out.println("C.method(): Beginning execution");
        nextMethod(); // Call the next method in the chain
        System.out.println("C.method(): End of execution");
    }
    
    public void methodC() {
        System.out.println("C.methodC(): Unique method of class C");
    }
}
```

### 4. Creating a Class with Multiple Inheritance

Now you can create a class that inherits from multiple classes:

```java
package example;

import inheritance.annotations.Mixin;

@Mixin({B.class, C.class})
public class D extends SomeInterfaceRoot {
    @Override
    public void method() {
        System.out.println("D.method(): Beginning execution");
        nextMethod(); // Call the next method in the chain
        System.out.println("D.method(): End of execution");
    }
    
    public void methodD() {
        System.out.println("D.methodD(): Unique method of class D");
    }
}
```

### 5. Creating Instances Using the Factory

Use `MixinFactory` to create class instances:

```java
import inheritance.factory.MixinFactory;

public class Main {
    public static void main(String[] args) {
        // Enable debug output (optional)
        MixinFactory.setDebugEnabled(true);
        
        // Create an instance of a class with multiple inheritance
        D d = MixinFactory.createInstance(D.class);
        
        // Call a method that will be executed along the inheritance chain
        d.method();
        
        // Call a unique method of the class
        d.methodD();
    }
}
```

When executing the `d.method()` method, the order of calls will be observed according to the topological sorting of the inheritance hierarchy:

```
D.method(): Beginning execution
B.method(): Beginning execution
A.method(): Executing method in base class
B.method(): End of execution
D.method(): End of execution
```

### 6. Accessing Parent Class Methods

You can access the parent object through the `parent` field or the `getParent()` method:

```java
// In class D
public void callParentMethods() {
    if (parent instanceof B) {
        ((B)parent).methodB();
    }
}
```

## Advanced Usage Scenarios

### Creating a Complex Inheritance Hierarchy

You can create more complex inheritance structures:

```java
@Mixin({B.class, C.class, E.class})
public class D extends SomeInterfaceRoot {
    // implementation
}

@Mixin({A.class, D.class})
public class F extends SomeInterfaceRoot {
    // implementation
}
```

### Managing Debug Information

```java
// Enable/disable debug output
MixinFactory.setDebugEnabled(true); // or false

// Run with a command line parameter
java -cp bin Main --debug=true
```



## Limitations

1. The `@Root` annotation should be applied to only one interface in the project
2. The current implementation creates a linear inheritance chain according to the topological sorting order
3. Each class must extend the generated `[InterfaceName]Root` class
4. The annotation processor must be included in the compilation process

## Requirements

- Java 11 or higher
- Support for annotations and annotation processors

## Project Structure

```
src/main/java/
├── example/diamond/        # Example classes
├── inheritance/
│   ├── annotations/        # Inheritance annotations
│   ├── factory/            # Object creation factory
│   └── processor/          # Annotation processor
└── [Demo classes]
```

## License

MIT License

