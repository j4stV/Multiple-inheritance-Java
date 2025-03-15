@echo off
echo === Скрипт компиляции проекта ромбовидного наследования ===

REM Очищаем директорию bin и создаем её заново
if exist bin rmdir /s /q bin
mkdir bin
mkdir bin\META-INF\services

REM Компилируем аннотации
echo Компиляция аннотаций...
javac -d bin src/main/java/inheritance/annotations/*.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции аннотаций
    exit /b %ERRORLEVEL%
)

REM Компилируем процессор аннотаций
echo Компиляция процессора аннотаций...
javac -d bin -cp bin src/main/java/inheritance/processor/*.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции процессора аннотаций
    exit /b %ERRORLEVEL%
)

REM Создаем файл службы для процессора аннотаций
echo inheritance.processor.RootProcessor > bin\META-INF\services\javax.annotation.processing.Processor

REM Компилируем фабрику
echo Компиляция фабрики...
javac -d bin -cp bin src/main/java/inheritance/factory/*.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции фабрики
    exit /b %ERRORLEVEL%
)

REM Компилируем корневой интерфейс
echo Компиляция корневого интерфейса...
javac -d bin -cp bin -processor inheritance.processor.RootProcessor src/main/java/example/diamond/SomeInterface.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции SomeInterface
    exit /b %ERRORLEVEL%
)

REM Компилируем класс A
echo Компиляция класса A...
javac -d bin -cp bin src/main/java/example/diamond/A.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции класса A
    exit /b %ERRORLEVEL%
)

REM Компилируем классы B и C
echo Компиляция классов B и C...
javac -d bin -cp bin src/main/java/example/diamond/B.java src/main/java/example/diamond/C.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции классов B и C
    exit /b %ERRORLEVEL%
)

REM Компилируем класс D
echo Компиляция класса D...
javac -d bin -cp bin src/main/java/example/diamond/D.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции класса D
    exit /b %ERRORLEVEL%
)

REM Компилируем тестовый класс
echo Компиляция тестового класса...
javac -d bin -cp bin src/main/java/example/diamond/DiamondTest.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции тестового класса
    exit /b %ERRORLEVEL%
)

REM Компилируем основной класс
echo Компиляция Main класса...
javac -d bin -cp bin src/main/java/Main.java
if %ERRORLEVEL% neq 0 (
    echo Ошибка при компиляции Main класса
    exit /b %ERRORLEVEL%
)

echo Компиляция успешно завершена!
echo Выполните 'java -cp bin Main' для запуска программы
echo Или выполните 'java -cp bin example.diamond.DiamondTest' для запуска теста