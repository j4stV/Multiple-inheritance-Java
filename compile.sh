#!/bin/bash
echo "=== Diamond inheritance project compilation script ==="

# Clean up and create directories
rm -rf bin
mkdir -p bin

# Compile annotations
echo "Compiling annotations..."
javac -d bin src/main/java/inheritance/annotations/Root.java src/main/java/inheritance/annotations/Mixin.java
if [ $? -ne 0 ]; then
    echo "Error compiling annotations"
    exit 1
fi

# Compile annotation processor
echo "Compiling annotation processor..."
javac -d bin -cp bin src/main/java/inheritance/processor/RootProcessor.java
if [ $? -ne 0 ]; then
    echo "Error compiling annotation processor"
    exit 1
fi

# Create directories for service file
mkdir -p bin/META-INF/services

# Create service file for annotation processor
echo "inheritance.processor.RootProcessor" > bin/META-INF/services/javax.annotation.processing.Processor

# Compile factory
echo "Compiling factory..."
javac -d bin -cp bin src/main/java/inheritance/factory/MixinFactory.java
if [ $? -ne 0 ]; then
    echo "Error compiling factory"
    exit 1
fi

# Compile root interface
echo "Compiling root interface..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/SomeInterface.java
if [ $? -ne 0 ]; then
    echo "Error compiling root interface"
    exit 1
fi

# Compile class A
echo "Compiling class A..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/A.java
if [ $? -ne 0 ]; then
    echo "Error compiling class A"
    exit 1
fi

# Compile classes B and C
echo "Compiling classes B and C..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/B.java src/main/java/example/diamond/C.java
if [ $? -ne 0 ]; then
    echo "Error compiling classes B and C"
    exit 1
fi

# Compile class E
echo "Compiling class E..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/E.java
if [ $? -ne 0 ]; then
    echo "Error compiling class E"
    exit 1
fi

# Compile class D
echo "Compiling class D..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/D.java
if [ $? -ne 0 ]; then
    echo "Error compiling class D"
    exit 1
fi

# Compile class F
echo "Compiling class F..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/F.java
if [ $? -ne 0 ]; then
    echo "Error compiling class F"
    exit 1
fi

# Compile Main class
echo "Compiling Main class..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/Main.java
if [ $? -ne 0 ]; then
    echo "Error compiling Main class"
    exit 1
fi

echo "Compilation completed successfully!"
echo "Run 'java -cp bin Main' to execute the program" 