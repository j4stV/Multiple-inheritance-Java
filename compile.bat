@echo off
echo === Diamond inheritance project compilation script ===

REM Cleanup and directory creation
if exist bin rmdir /s /q bin
mkdir bin

REM Compile annotations
echo Compiling annotations...
javac -d bin src/main/java/inheritance/annotations/Root.java src/main/java/inheritance/annotations/Mixin.java
if errorlevel 1 (
  echo Error compiling annotations
  exit /b 1
)

REM Compile annotation processor
echo Compiling annotation processor...
javac -d bin -cp bin src/main/java/inheritance/processor/RootProcessor.java
if errorlevel 1 (
  echo Error compiling annotation processor
  exit /b 1
)

REM Create service file directories
mkdir bin\META-INF
mkdir bin\META-INF\services

REM Create service file for annotation processor
echo inheritance.processor.RootProcessor > bin\META-INF\services\javax.annotation.processing.Processor

REM Compile factory
echo Compiling factory...
javac -d bin -cp bin src/main/java/inheritance/factory/MixinFactory.java
if errorlevel 1 (
  echo Error compiling factory
  exit /b 1
)

REM Compile root interface
echo Compiling root interface...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/SomeInterface.java
if errorlevel 1 (
  echo Error compiling root interface
  exit /b 1
)

REM Compile class A
echo Compiling class A...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/A.java
if errorlevel 1 (
  echo Error compiling class A
  exit /b 1
)

REM Compile classes B and C
echo Compiling classes B and C...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/B.java src/main/java/example/diamond/C.java
if errorlevel 1 (
  echo Error compiling classes B and C
  exit /b 1
)

REM Compile class E
echo Compiling class E...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/E.java
if errorlevel 1 (
  echo Error compiling class E
  exit /b 1
)

REM Compile class D
echo Compiling class D...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/D.java
if errorlevel 1 (
  echo Error compiling class D
  exit /b 1
)

REM Compile class F
echo Compiling class F...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/F.java
if errorlevel 1 (
  echo Error compiling class F
  exit /b 1
)

REM Compile Main class
echo Compiling Main class...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/Main.java
if errorlevel 1 (
  echo Error compiling Main class
  exit /b 1
)

echo Compilation completed successfully!
echo Run 'java -cp bin Main' to execute the program