#!/bin/bash
echo "=== Скрипт компиляции проекта ромбовидного наследования ==="

# Очищаем директорию bin и создаем её заново
if [ -d bin ]; then
  rm -rf bin
fi
mkdir -p bin/META-INF/services

# Компилируем аннотации
echo "Компиляция аннотаций..."
javac -d bin src/main/java/inheritance/annotations/*.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции аннотаций"
  exit 1
fi

# Компилируем процессор аннотаций
echo "Компиляция процессора аннотаций..."
javac -d bin -cp bin src/main/java/inheritance/processor/*.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции процессора аннотаций"
  exit 1
fi

# Создаем файл службы для процессора аннотаций
echo "inheritance.processor.RootProcessor" > bin/META-INF/services/javax.annotation.processing.Processor

# Компилируем фабрику
echo "Компиляция фабрики..."
javac -d bin -cp bin src/main/java/inheritance/factory/*.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции фабрики"
  exit 1
fi

# Компилируем корневой интерфейс
echo "Компиляция корневого интерфейса..."
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/SomeInterface.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции SomeInterface"
  exit 1
fi

# Компилируем класс A
echo "Компиляция класса A..."
javac -d bin -cp bin src/main/java/example/diamond/A.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции класса A"
  exit 1
fi

# Компилируем классы B и C
echo "Компиляция классов B и C..."
javac -d bin -cp bin src/main/java/example/diamond/B.java src/main/java/example/diamond/C.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции классов B и C"
  exit 1
fi

# Компилируем класс D
echo "Компиляция класса D..."
javac -d bin -cp bin src/main/java/example/diamond/D.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции класса D"
  exit 1
fi

# Компилируем тестовый класс
echo "Компиляция тестового класса..."
javac -d bin -cp bin src/main/java/example/diamond/DiamondTest.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции тестового класса"
  exit 1
fi

# Компилируем основной класс
echo "Компиляция Main класса..."
javac -d bin -cp bin src/main/java/Main.java
if [ $? -ne 0 ]; then
  echo "Ошибка при компиляции Main класса"
  exit 1
fi

echo "Компиляция успешно завершена!"
echo "Выполните 'java -cp bin Main' для запуска программы"
echo "Или выполните 'java -cp bin example.diamond.DiamondTest' для запуска теста" 