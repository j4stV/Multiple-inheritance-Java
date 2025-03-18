#!/bin/bash
echo "=== Diamond inheritance project compilation script ==="

# Clean bin directory and create it again
if [ -d bin ]; then
  rm -rf bin
fi
mkdir -p bin/META-INF/services

# Compile annotations
echo "Compiling annotations..."
javac -d bin src/main/java/inheritance/annotations/*.java
if [ $? -ne 0 ]; then
  echo "Error compiling annotations"
  exit 1
fi

# Compile annotation processor
echo "Compiling annotation processor..."
javac -d bin -cp bin src/main/java/inheritance/processor/*.java
if [ $? -ne 0 ]; then
  echo "Error compiling annotation processor"
  exit 1
fi

# Create service file for annotation processor
echo "inheritance.processor.RootProcessor" > bin/META-INF/services/javax.annotation.processing.Processor

# Compile factory
echo "Compiling factory..."
javac -d bin -cp bin src/main/java/inheritance/factory/*.java
if [ $? -ne 0 ]; then
  echo "Error compiling factory"
  exit 1
fi

# Compile root interface
echo "Compiling root interface..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/SomeInterface.java
if [ $? -ne 0 ]; then
  echo "Error compiling SomeInterface"
  exit 1
fi

# Compile class A
echo "Compiling class A..."
javac -d bin -cp bin src/main/java/example/diamond/A.java
if [ $? -ne 0 ]; then
  echo "Error compiling class A"
  exit 1
fi

# Compile classes B and C
echo "Compiling classes B and C..."
javac -d bin -cp bin src/main/java/example/diamond/B.java src/main/java/example/diamond/C.java
if [ $? -ne 0 ]; then
  echo "Error compiling classes B and C"
  exit 1
fi

# Compile class D
echo "Compiling class D..."
javac -d bin -cp bin src/main/java/example/diamond/D.java
if [ $? -ne 0 ]; then
  echo "Error compiling class D"
  exit 1
fi

# Compile test class
echo "Compiling test class..."
javac -d bin -cp bin src/main/java/example/diamond/DiamondTest.java
if [ $? -ne 0 ]; then
  echo "Error compiling test class"
  exit 1
fi

# Compile main class
echo "Compiling Main class..."
javac -d bin -cp bin src/main/java/Main.java
if [ $? -ne 0 ]; then
  echo "Error compiling Main class"
  exit 1
fi

echo "Compilation successful!"
echo "Run 'java -cp bin Main' to start the program"
echo "Or run 'java -cp bin example.diamond.DiamondTest' to start the test" 