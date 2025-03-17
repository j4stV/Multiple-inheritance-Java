@echo off
echo === Скрипт компиляции проекта ромбовидного наследования ===

REM Очистка и создание директорий
if exist bin rmdir /s /q bin
mkdir bin

REM Компилируем аннотации
echo Компиляция аннотаций...
javac -d bin src/main/java/inheritance/annotations/Root.java src/main/java/inheritance/annotations/Mixin.java
if errorlevel 1 (
  echo Ошибка при компиляции аннотаций
  exit /b 1
)

REM Компилируем процессор аннотаций
echo Компиляция процессора аннотаций...
javac -d bin -cp bin src/main/java/inheritance/processor/RootProcessor.java
if errorlevel 1 (
  echo Ошибка при компиляции процессора аннотаций
  exit /b 1
)

REM Создаем директории для сервисного файла
mkdir bin\META-INF
mkdir bin\META-INF\services

REM Создаем сервисный файл для процессора аннотаций
echo inheritance.processor.RootProcessor > bin\META-INF\services\javax.annotation.processing.Processor

REM Компилируем фабрику
echo Компиляция фабрики...
javac -d bin -cp bin src/main/java/inheritance/factory/MixinFactory.java
if errorlevel 1 (
  echo Ошибка при компиляции фабрики
  exit /b 1
)

REM Компилируем корневой интерфейс
echo Компиляция корневого интерфейса...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/SomeInterface.java
if errorlevel 1 (
  echo Ошибка при компиляции корневого интерфейса
  exit /b 1
)

REM Компилируем класс A
echo Компиляция класса A...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/A.java
if errorlevel 1 (
  echo Ошибка при компиляции класса A
  exit /b 1
)

REM Компилируем классы B и C
echo Компиляция классов B и C...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/B.java src/main/java/example/diamond/C.java
if errorlevel 1 (
  echo Ошибка при компиляции классов B и C
  exit /b 1
)

REM Компилируем класс E
echo Компиляция класса E...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/E.java
if errorlevel 1 (
  echo Ошибка при компиляции класса E
  exit /b 1
)

REM Компилируем класс D
echo Компиляция класса D...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/D.java
if errorlevel 1 (
  echo Ошибка при компиляции класса D
  exit /b 1
)

REM Компилируем класс F
echo Компиляция класса F...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/F.java
if errorlevel 1 (
  echo Ошибка при компиляции класса F
  exit /b 1
)

REM Компилируем тестовый класс
echo Компиляция тестового класса...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/DiamondTest.java
if errorlevel 1 (
  echo Ошибка при компиляции тестового класса
  exit /b 1
)

REM Компилируем простой тестовый класс
echo Компиляция простого тестового класса...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/SimpleTest.java
if errorlevel 1 (
  echo Ошибка при компиляции простого тестового класса
  exit /b 1
)

REM Компилируем пример сложного наследования
echo Компиляция примера сложного наследования...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/ComplexInheritanceExample.java
if errorlevel 1 (
  echo Ошибка при компиляции примера сложного наследования
  exit /b 1
)

REM Компилируем демонстрацию структуры наследования
echo Компиляция демонстрации структуры наследования...
javac -d bin -cp bin src/main/java/InheritanceStructureDemo.java
if errorlevel 1 (
  echo Ошибка при компиляции демонстрации структуры наследования
  exit /b 1
)

REM Компилируем новую демонстрацию наследования методов
echo Компиляция демонстрации наследования методов...
javac -d bin -cp bin src/main/java/MethodInheritanceDemo.java
if errorlevel 1 (
  echo Ошибка при компиляции демонстрации наследования методов
  exit /b 1
)

REM Компилируем Main класс
echo Компиляция Main класса...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/Main.java
if errorlevel 1 (
  echo Ошибка при компиляции Main класса
  exit /b 1
)

echo Компиляция успешно завершена!
echo Выполните 'java -cp bin Main' для запуска программы
echo Или выполните 'java -cp bin example.diamond.DiamondTest' для запуска теста
echo Или выполните 'java -cp bin SimpleTest' для запуска простого теста
echo Или выполните 'java -cp bin ComplexInheritanceExample' для запуска примера сложного наследования
echo Или выполните 'java -cp bin MethodInheritanceDemo' для демонстрации наследования методов