## Описание

Эта библиотека предоставляет реализацию механизма множественного наследования для Java с использованием аннотаций, процессора аннотаций и фабрики для создания экземпляров классов. Библиотека позволяет преодолеть ограничение Java на одиночное наследование, сохраняя при этом типобезопасность и предсказуемое поведение.

## Компоненты библиотеки

1. **Аннотации**:
   - `@Root` - помечает интерфейс как корневой в иерархии наследования
   - `@Mixin` - указывает родительские классы, от которых происходит наследование

2. **Процессор аннотаций**:
   - `RootProcessor` - генерирует базовый класс для интерфейса, помеченного аннотацией `@Root`

3. **Фабрика**:
   - `MixinFactory` - создает экземпляры классов с корректно настроенной цепочкой наследования

## Инструкция по использованию

### 1. Настройка проекта

Чтобы начать использовать библиотеку, добавьте ее JAR-файл в ваш проект и убедитесь, что процессор аннотаций включен в процесс компиляции.

Для сборки с помощью Maven добавьте в `pom.xml`:

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

Для сборки с помощью Gradle добавьте в `build.gradle`:

```groovy
dependencies {
    implementation 'com.example:java-multiple-inheritance:1.0.0'
    annotationProcessor 'com.example:java-multiple-inheritance:1.0.0'
}
```

### 2. Создание корневого интерфейса

Определите корневой интерфейс и пометьте его аннотацией `@Root`:

```java
package example;

import inheritance.annotations.Root;

@Root
public interface SomeInterface {
    void method();
}
```

После компиляции процессор аннотаций автоматически сгенерирует класс `SomeInterfaceRoot`.

### 3. Создание классов в иерархии наследования

Создайте базовый класс, который будет расширять сгенерированный класс:

```java
package example;

public class A extends SomeInterfaceRoot {
    @Override
    public void method() {
        System.out.println("A.method(): Выполнение метода в базовом классе");
        nextMethod(); // Вызов метода следующего в цепочке
    }
}
```

Затем создайте классы, наследующие от базового, с указанием родителей через аннотацию `@Mixin`:

```java
package example;

import inheritance.annotations.Mixin;

@Mixin(A.class)
public class B extends SomeInterfaceRoot {
    @Override
    public void method() {
        System.out.println("B.method(): Начало выполнения");
        nextMethod(); // Вызов метода следующего в цепочке
        System.out.println("B.method(): Конец выполнения");
    }
    
    public void methodB() {
        System.out.println("B.methodB(): Уникальный метод класса B");
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
        System.out.println("C.method(): Начало выполнения");
        nextMethod(); // Вызов метода следующего в цепочке
        System.out.println("C.method(): Конец выполнения");
    }
    
    public void methodC() {
        System.out.println("C.methodC(): Уникальный метод класса C");
    }
}
```

### 4. Создание класса с множественным наследованием

Теперь вы можете создать класс, который наследует от нескольких классов:

```java
package example;

import inheritance.annotations.Mixin;

@Mixin({B.class, C.class})
public class D extends SomeInterfaceRoot {
    @Override
    public void method() {
        System.out.println("D.method(): Начало выполнения");
        nextMethod(); // Вызов метода следующего в цепочке
        System.out.println("D.method(): Конец выполнения");
    }
    
    public void methodD() {
        System.out.println("D.methodD(): Уникальный метод класса D");
    }
}
```

### 5. Создание экземпляров с помощью фабрики

Используйте `MixinFactory` для создания экземпляров классов:

```java
import inheritance.factory.MixinFactory;

public class Main {
    public static void main(String[] args) {
        // Включаем вывод отладочной информации (по желанию)
        MixinFactory.setDebugEnabled(true);
        
        // Создаем экземпляр класса с множественным наследованием
        D d = MixinFactory.createInstance(D.class);
        
        // Вызываем метод, который будет выполнен по цепочке наследования
        d.method();
        
        // Вызываем уникальный метод класса
        d.methodD();
    }
}
```

При выполнении метода `d.method()` будет соблюдаться порядок вызовов в соответствии с топологической сортировкой иерархии наследования:

```
D.method(): Начало выполнения
B.method(): Начало выполнения
A.method(): Выполнение метода в базовом классе
B.method(): Конец выполнения
D.method(): Конец выполнения
```

### 6. Доступ к методам родительских классов

Вы можете получить доступ к родительскому объекту через поле `parent` или метод `getParent()`:

```java
// В классе D
public void callParentMethods() {
    if (parent instanceof B) {
        ((B)parent).methodB();
    }
}
```

## Продвинутые сценарии использования

### Создание сложной иерархии наследования

Вы можете создавать более сложные структуры наследования:

```java
@Mixin({B.class, C.class, E.class})
public class D extends SomeInterfaceRoot {
    // реализация
}

@Mixin({A.class, D.class})
public class F extends SomeInterfaceRoot {
    // реализация
}
```

### Управление отладочной информацией

```java
// Включение/выключение отладочного вывода
MixinFactory.setDebugEnabled(true); // или false

// Запуск с параметром командной строки
java -cp bin Main --debug=true
```

## Компиляция и запуск

### Компиляция проекта

Проект можно скомпилировать с помощью скрипта `compile.bat` (Windows) или `compile.sh` (Linux/macOS):

```bash
.\compile.bat
```

### Запуск примеров

После компиляции вы можете запустить примеры:

```bash
# Основной пример
java -cp bin Main

# Тест ромбовидного наследования
java -cp bin example.diamond.DiamondTest

# Простой тест
java -cp bin SimpleTest

# Пример сложного наследования
java -cp bin ComplexInheritanceExample

# Демонстрация наследования методов
java -cp bin MethodInheritanceDemo
```

## Ограничения

1. Аннотация `@Root` должна быть применена только к одному интерфейсу в проекте
2. Текущая реализация создает линейную цепочку наследования по порядку топологической сортировки
3. Каждый класс должен расширять сгенерированный класс `[ИмяИнтерфейса]Root`
4. Процессор аннотаций должен быть включен в процесс компиляции

## Требования

- Java 11 или выше
- Поддержка аннотаций и процессоров аннотаций

## Структура проекта

```
src/main/java/
├── example/diamond/        # Примеры классов
├── inheritance/
│   ├── annotations/        # Аннотации для наследования
│   ├── factory/            # Фабрика для создания объектов
│   └── processor/          # Процессор аннотаций
└── [Демонстрационные классы]
```

## Лицензия

MIT License
