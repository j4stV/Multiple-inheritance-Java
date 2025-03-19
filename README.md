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

## Usage Instructions

### 1. Project Setup

To start using the library, add its JAR file to your project and ensure that the annotation processor is included in the compilation process.

For Maven build, add to `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.example</groupId>
        <artifactId>java-multiple-inheritance</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>11</source>
                <target>11</target>
                <annotationProcessorPaths>
                    <path>
                        <groupId>com.example</groupId>
                        <artifactId>java-multiple-inheritance</artifactId>
                        <version>1.0.0</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

For Gradle build, add to `build.gradle`:

```groovy
dependencies {
    implementation 'com.example:java-multiple-inheritance:1.0.0'
    annotationProcessor 'com.example:java-multiple-inheritance:1.0.0'
}
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

## Compilation and Execution

### Project Compilation

The project can be compiled using the `compile.bat` script (Windows) or `compile.sh` (Linux/macOS):

```bash
.\compile.bat
```

### Running Examples

After compilation, you can run the examples:

```bash
# Main example
java -cp bin Main

# Diamond inheritance test
java -cp bin example.diamond.DiamondTest

# Simple test
java -cp bin SimpleTest

# Complex inheritance example
java -cp bin ComplexInheritanceExample

# Method inheritance demo
java -cp bin MethodInheritanceDemo
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

## Подключение фреймворка в проект

### Gradle KTS

```kotlin
// Добавление Maven репозитория (если публикуете в Maven Central)
repositories {
    mavenCentral()
    // Если у вас локальный репозиторий
    // maven { url = uri("file:///path/to/local/repo") }
}

// Добавление зависимости
dependencies {
    implementation("com.java.multiple.inheritance:java-multiple-inheritance:1.0.0")
    annotationProcessor("com.java.multiple.inheritance:java-multiple-inheritance:1.0.0")
}
```

### Gradle Groovy

```groovy
// Добавление Maven репозитория (если публикуете в Maven Central)
repositories {
    mavenCentral()
    // Если у вас локальный репозиторий
    // maven { url 'file:///path/to/local/repo' }
}

// Добавление зависимости
dependencies {
    implementation 'com.java.multiple.inheritance:java-multiple-inheritance:1.0.0'
    annotationProcessor 'com.java.multiple.inheritance:java-multiple-inheritance:1.0.0'
}
```

### Maven

```xml
<dependency>
    <groupId>com.java.multiple.inheritance</groupId>
    <artifactId>java-multiple-inheritance</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Инструкция по созданию локального репозитория

Если вы не хотите публиковать фреймворк в Maven Central, вы можете создать локальный репозиторий:

```bash
# Сборка и публикация в локальный репозиторий
./gradlew publishToMavenLocal
```

После этого JAR-файлы будут доступны в вашем локальном Maven репозитории (обычно ~/.m2/repository/).

## Пример использования в проекте

1. Создайте интерфейс и пометьте его аннотацией `@Root`:

```java
import inheritance.annotations.Root;

@Root
public interface MyInterface {
    void someMethod();
}
```

2. Реализуйте базовый класс, расширяющий сгенерированный `MyInterfaceRoot`:

```java
public class BaseClass extends MyInterfaceRoot {
    @Override
    public void someMethod() {
        System.out.println("BaseClass implementation");
        nextSomeMethod(); // Вызываем метод следующего в цепочке
    }
}
```

3. Создайте класс с множественным наследованием используя аннотацию `@Mixin`:

```java
import inheritance.annotations.Mixin;

@Mixin({BaseClass.class, OtherClass.class})
public class MultiInheritClass extends MyInterfaceRoot {
    @Override
    public void someMethod() {
        System.out.println("MultiInheritClass starting");
        nextSomeMethod(); // Перенаправляем вызов к родительским классам
        System.out.println("MultiInheritClass ending");
    }
}
```

4. Создайте экземпляр с помощью фабрики:

```java
import inheritance.factory.MixinFactory;

public class Main {
    public static void main(String[] args) {
        // Создаем экземпляр класса с множественным наследованием
        MultiInheritClass instance = MixinFactory.createInstance(MultiInheritClass.class);
        
        // Вызываем метод, который будет выполнен по цепочке наследования
        instance.someMethod();
    }
}
```

## Сборка и установка фреймворка

Для сборки фреймворка с использованием Gradle выполните:

```bash
# Клонирование репозитория
git clone https://github.com/yourusername/java-multiple-inheritance.git
cd java-multiple-inheritance

# Сборка фреймворка
./gradlew build

# Публикация в локальный Maven репозиторий
./gradlew publishToMavenLocal
```

После публикации фреймворк можно подключить в других проектах через систему сборки:

```kotlin
// build.gradle.kts (Kotlin DSL)
dependencies {
    implementation("com.java.multiple.inheritance:java-multiple-inheritance:1.0.0")
    annotationProcessor("com.java.multiple.inheritance:java-multiple-inheritance:1.0.0")
}
```

## Примеры использования

В директории `examples/simple-project` находится пример использования фреймворка для создания класса `Amphibian` с множественным наследованием от `Car` и `Boat`.

Для запуска примера:

```bash
cd examples/simple-project
./gradlew run
```
