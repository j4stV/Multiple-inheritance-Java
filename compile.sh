#!/bin/bash
echo "=== Diamond inheritance project compilation script ==="

# Очистка и создание директорий
rm -rf bin
mkdir -p bin

# Компилируем аннотации
echo "Compiling annotations..."
javac -d bin src/main/java/inheritance/annotations/Root.java src/main/java/inheritance/annotations/Mixin.java
if [ $? -ne 0 ]; then
    echo "Error compiling annotations"
    exit 1
fi

# Компилируем процессор аннотаций
echo "Compiling annotation processor..."
javac -d bin -cp bin src/main/java/inheritance/processor/RootProcessor.java
if [ $? -ne 0 ]; then
    echo "Error compiling annotation processor"
    exit 1
fi

# Создаем директории для файла сервиса
mkdir -p bin/META-INF/services

# Создаем файл сервиса для процессора аннотаций
echo "inheritance.processor.RootProcessor" > bin/META-INF/services/javax.annotation.processing.Processor

# Компилируем фабрику
echo "Compiling factory..."
javac -d bin -cp bin src/main/java/inheritance/factory/MixinFactory.java
if [ $? -ne 0 ]; then
    echo "Error compiling factory"
    exit 1
fi

# Компилируем корневой интерфейс
echo "Compiling root interface..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/SomeInterface.java
if [ $? -ne 0 ]; then
    echo "Error compiling root interface"
    exit 1
fi

# Компилируем класс A
echo "Compiling class A..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/A.java
if [ $? -ne 0 ]; then
    echo "Error compiling class A"
    exit 1
fi

# Компилируем классы B и C
echo "Compiling classes B and C..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/B.java src/main/java/example/diamond/C.java
if [ $? -ne 0 ]; then
    echo "Error compiling classes B and C"
    exit 1
fi

# Компилируем класс E
echo "Compiling class E..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/E.java
if [ $? -ne 0 ]; then
    echo "Error compiling class E"
    exit 1
fi

# Компилируем класс D
echo "Compiling class D..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/D.java
if [ $? -ne 0 ]; then
    echo "Error compiling class D"
    exit 1
fi

# Компилируем класс F
echo "Compiling class F..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/F.java
if [ $? -ne 0 ]; then
    echo "Error compiling class F"
    exit 1
fi

# Компилируем класс Main
echo "Compiling Main class..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/Main.java
if [ $? -ne 0 ]; then
    echo "Error compiling Main class"
    exit 1
fi

echo "Compilation completed successfully!"
echo "Run 'java -cp bin Main' to execute the program" 