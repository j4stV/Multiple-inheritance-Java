# Множественное наследование в Java

Проект демонстрирует реализацию множественного наследования в Java с использованием аннотаций и обработки во время компиляции.

## Описание

Проект реализует механизм множественного наследования в гомогенной иерархии в Java. Гомогенная иерархия означает, что у нее есть единый корень (интерфейс), и все операции над иерархией определяются им.

Структура проекта:
- `inheritance.annotations` - аннотации для определения множественного наследования
- `inheritance.processor` - процессор аннотаций для генерации кода
- `example` - пример использования

## Основные компоненты

### Аннотации
- `@Root` - помечает интерфейс как корневой в иерархии наследования
- `@Mixin` - указывает родительские классы для множественного наследования

### Процессор аннотаций
Процессор аннотаций `RootProcessor` обрабатывает интерфейсы с аннотацией `@Root` и генерирует для них корневые классы (например, для интерфейса `Animal` будет сгенерирован класс `AnimalRoot`).

Корневой класс:
- Реализует соответствующий интерфейс
- Предоставляет методы nextXXX для каждого метода интерфейса, которые позволяют вызвать методы родительских классов (call-next-method)
- Обрабатывает аннотацию `@Mixin` для создания родительских объектов

## Пример использования

1. Создайте интерфейс с аннотацией `@Root`:
```java
@Root
public interface Animal {
    void makeSound();
    String getType();
}
```

2. Создайте класс-родитель, реализующий этот интерфейс:
```java
public class Dog implements Animal {
    @Override
    public void makeSound() {
        System.out.println("Гав-гав!");
    }

    @Override
    public String getType() {
        return "Собака";
    }
}
```

3. Создайте класс-наследник с аннотацией `@Mixin`:
```java
@Mixin(Dog.class)
public class MyPet extends AnimalRoot {
    @Override
    public void makeSound() {
        System.out.println("Мой питомец говорит: ");
        nextMakeSound(); // Вызываем метод родителя
        System.out.println("И хочет гулять!");
    }

    @Override
    public String getType() {
        String parentType = nextGetType(); // Получаем тип от родителя
        return "Мой любимый " + parentType;
    }
}
```

4. Используйте созданные классы:
```java
MyPet pet = new MyPet();
pet.makeSound();
System.out.println("Тип: " + pet.getType());
```

## Компиляция и запуск

Процессор аннотаций активируется при компиляции исходного кода. Для корректной работы процессора и генерации Root-классов выполните следующие шаги:

### Для Windows

1. **Создание директории для скомпилированных файлов:**
```
mkdir bin
```

2. **Компиляция аннотаций и процессора аннотаций:**
```
javac -d bin src/inheritance/annotations/*.java
javac -d bin -cp bin src/inheritance/processor/*.java
mkdir -p bin\META-INF\services
copy src\META-INF\services\javax.annotation.processing.Processor bin\META-INF\services\
```

3. **Компиляция интерфейса с аннотацией @Root (генерация корневого класса):**
```
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/example/Animal.java
```

4. **Компиляция остальных классов:**
```
javac -d bin -cp bin src/example/Dog.java src/example/MyPet.java
javac -d bin -cp bin src/Main.java
```

5. **Запуск приложения:**
```
java -cp bin Main
```

### Для Linux/Mac

1. **Создание директории для скомпилированных файлов:**
```
mkdir -p bin
```

2. **Компиляция аннотаций и процессора аннотаций:**
```
javac -d bin src/inheritance/annotations/*.java
javac -d bin -cp bin src/inheritance/processor/*.java
mkdir -p bin/META-INF/services
cp src/META-INF/services/javax.annotation.processing.Processor bin/META-INF/services/
```

3. **Компиляция интерфейса с аннотацией @Root (генерация корневого класса):**
```
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/example/Animal.java
```

4. **Компиляция остальных классов:**
```
javac -d bin -cp bin src/example/Dog.java src/example/MyPet.java
javac -d bin -cp bin src/Main.java
```

5. **Запуск приложения:**
```
java -cp bin Main
``` 