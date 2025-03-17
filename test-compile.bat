@echo off
echo === Скрипт компиляции и запуска тестов множественного наследования ===

REM Параметры компиляции и запуска
set JUNIT_PATH=lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar
set TEST_CLASSES=bin\test
set MAIN_CLASSES=bin

REM Создаем директории для тестов, если их нет
if not exist %TEST_CLASSES% mkdir %TEST_CLASSES%
if not exist lib mkdir lib

REM Проверяем, есть ли библиотеки JUnit, если нет - скачиваем
if not exist lib\junit-4.13.2.jar (
    echo Загрузка библиотек JUnit...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar', 'lib\junit-4.13.2.jar')"
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar', 'lib\hamcrest-core-1.3.jar')"
)

REM Очищаем директории перед компиляцией
if exist %TEST_CLASSES% rmdir /s /q %TEST_CLASSES%
mkdir %TEST_CLASSES%

REM Компилируем основной проект (если еще не скомпилирован)
echo Проверка компиляции основного проекта...
if not exist %MAIN_CLASSES%\inheritance\factory\MixinFactory.class (
    echo Основной проект не скомпилирован, запуск компиляции...
    call compile.bat
    if errorlevel 1 (
        echo Ошибка при компиляции основного проекта!
        exit /b 1
    )
)

REM Выбор теста для запуска
set TEST_TO_RUN=%1
if "%TEST_TO_RUN%"=="" (
    set TEST_TO_RUN=linear
)

echo Запуск тестов для %TEST_TO_RUN% наследования...

if "%TEST_TO_RUN%"=="linear" (
    REM Компилируем и запускаем тесты для линейного наследования
    echo Компиляция интерфейса для линейного наследования...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/TestLinearInterface.java
    if errorlevel 1 (
        echo Ошибка при компиляции интерфейса для линейного наследования!
        exit /b 1
    )
    
    echo Компиляция классов для линейного наследования...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/ClassA.java
    if errorlevel 1 (
        echo Ошибка при компиляции ClassA!
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/ClassB.java
    if errorlevel 1 (
        echo Ошибка при компиляции ClassB!
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/ClassC.java
    if errorlevel 1 (
        echo Ошибка при компиляции ClassC!
        exit /b 1
    )
    
    echo Компиляция теста линейного наследования...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/LinearInheritanceTest.java
    if errorlevel 1 (
        echo Ошибка при компиляции теста линейного наследования!
        exit /b 1
    )
    
    echo Запуск теста линейного наследования...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.linear.LinearInheritanceTest
) else if "%TEST_TO_RUN%"=="diamond" (
    REM Компилируем и запускаем тесты для ромбовидного наследования
    echo Компиляция интерфейса для ромбовидного наследования...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/DiamondInterface.java
    if errorlevel 1 (
        echo Ошибка при компиляции интерфейса для ромбовидного наследования!
        exit /b 1
    )
    
    echo Компиляция классов для ромбовидного наследования...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassA.java
    if errorlevel 1 (
        echo Ошибка при компиляции ClassA!
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassB.java
    if errorlevel 1 (
        echo Ошибка при компиляции ClassB!
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassC.java
    if errorlevel 1 (
        echo Ошибка при компиляции ClassC!
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassD.java
    if errorlevel 1 (
        echo Ошибка при компиляции ClassD!
        exit /b 1
    )
    
    echo Компиляция теста ромбовидного наследования...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/DiamondInheritanceTest.java
    if errorlevel 1 (
        echo Ошибка при компиляции теста ромбовидного наследования!
        exit /b 1
    )
    
    echo Запуск теста ромбовидного наследования...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.diamond.DiamondInheritanceTest
) else if "%TEST_TO_RUN%"=="cyclic" (
    REM Компилируем и запускаем тесты для циклического наследования
    echo Компиляция интерфейса для циклического наследования...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/CyclicInterface.java
    if errorlevel 1 (
        echo Ошибка при компиляции интерфейса для циклического наследования!
        exit /b 1
    )
    
    echo Компиляция классов для циклического наследования...
    REM Компилируем все классы одной командой, чтобы избежать проблем с циклическими зависимостями
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/ClassA.java src/test/java/inheritance/tests/cyclic/ClassB.java src/test/java/inheritance/tests/cyclic/ClassC.java
    if errorlevel 1 (
        echo Ошибка при компиляции классов для циклического наследования!
        exit /b 1
    )
    
    echo Компиляция теста циклического наследования...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/CyclicInheritanceTest.java
    if errorlevel 1 (
        echo Ошибка при компиляции теста циклического наследования!
        exit /b 1
    )
    
    echo Запуск теста циклического наследования...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.cyclic.CyclicInheritanceTest
) else if "%TEST_TO_RUN%"=="deepChain" (
    REM Компилируем и запускаем тесты для глубокой цепочки наследования
    if exist src\test\java\inheritance\tests\deepChain\*.java (
        echo Компиляция классов для глубокой цепочки наследования...
        
        REM Поиск всех Java файлов в директории
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\deepChain\*.java) do (
            set "java_files=!java_files! %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Ошибка при компиляции классов для глубокой цепочки наследования!
            exit /b 1
        )
        
        echo Запуск теста глубокой цепочки наследования...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.deepChain.DeepChainInheritanceTest
    ) else (
        echo Тест глубокой цепочки наследования пропущен - файлы не найдены.
    )
) else if "%TEST_TO_RUN%"=="repeatedAncestor" (
    REM Компилируем и запускаем тесты для случая повторяющегося предка
    if exist src\test\java\inheritance\tests\repeatedAncestor\*.java (
        echo Компиляция классов для случая повторяющегося предка...
        
        REM Поиск всех Java файлов в директории
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\repeatedAncestor\*.java) do (
            set "java_files=!java_files! %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Ошибка при компиляции классов для случая повторяющегося предка!
            exit /b 1
        )
        
        echo Запуск теста для случая повторяющегося предка...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.repeatedAncestor.RepeatedAncestorTest
    ) else (
        echo Тест для случая повторяющегося предка пропущен - файлы не найдены.
    )
) else if "%TEST_TO_RUN%"=="nestedDiamond" (
    REM Компилируем и запускаем тесты для вложенного ромбовидного наследования
    if exist src\test\java\inheritance\tests\nestedDiamond\*.java (
        echo Компиляция классов для вложенного ромбовидного наследования...
        
        REM Поиск всех Java файлов в директории
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\nestedDiamond\*.java) do (
            set "java_files=!java_files! %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Ошибка при компиляции классов для вложенного ромбовидного наследования!
            exit /b 1
        )
        
        echo Запуск теста вложенного ромбовидного наследования...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.nestedDiamond.NestedDiamondTest
    ) else (
        echo Тест вложенного ромбовидного наследования пропущен - файлы не найдены.
    )
) else if "%TEST_TO_RUN%"=="all" (
    REM Запускаем все тесты
    
    REM Линейное наследование
    echo Компиляция классов для линейного наследования...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/*.java
    if errorlevel 1 (
        echo Ошибка при компиляции классов для линейного наследования!
        exit /b 1
    )
    
    echo Запуск теста линейного наследования...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.linear.LinearInheritanceTest
    
    REM Ромбовидное наследование
    echo Компиляция классов для ромбовидного наследования...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/*.java
    if errorlevel 1 (
        echo Ошибка при компиляции классов для ромбовидного наследования!
        exit /b 1
    )
    
    echo Запуск теста ромбовидного наследования...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.diamond.DiamondInheritanceTest
    
    REM Циклическое наследование
    echo Компиляция классов для циклического наследования...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/*.java
    if errorlevel 1 (
        echo Ошибка при компиляции классов для циклического наследования!
        exit /b 1
    )
    
    echo Запуск теста циклического наследования...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.cyclic.CyclicInheritanceTest
    
    REM Глубокая цепочка наследования
    if exist src\test\java\inheritance\tests\deepChain\*.java (
        echo Компиляция классов для глубокой цепочки наследования...
        
        REM Поиск всех Java файлов в директории
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\deepChain\*.java) do (
            set "java_files=!java_files! %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Ошибка при компиляции классов для глубокой цепочки наследования!
            exit /b 1
        )
        
        echo Запуск теста глубокой цепочки наследования...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.deepChain.DeepChainInheritanceTest
    ) else (
        echo Тест глубокой цепочки наследования пропущен - файлы не найдены.
    )
    
    REM Случай повторяющегося предка
    if exist src\test\java\inheritance\tests\repeatedAncestor\*.java (
        echo Компиляция классов для случая повторяющегося предка...
        
        REM Поиск всех Java файлов в директории
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\repeatedAncestor\*.java) do (
            set "java_files=!java_files! %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Ошибка при компиляции классов для случая повторяющегося предка!
            exit /b 1
        )
        
        echo Запуск теста для случая повторяющегося предка...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.repeatedAncestor.RepeatedAncestorTest
    ) else (
        echo Тест для случая повторяющегося предка пропущен - файлы не найдены.
    )
    
    REM Вложенное ромбовидное наследование
    if exist src\test\java\inheritance\tests\nestedDiamond\*.java (
        echo Компиляция классов для вложенного ромбовидного наследования...
        
        REM Поиск всех Java файлов в директории
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\nestedDiamond\*.java) do (
            set "java_files=!java_files! %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Ошибка при компиляции классов для вложенного ромбовидного наследования!
            exit /b 1
        )
        
        echo Запуск теста вложенного ромбовидного наследования...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.nestedDiamond.NestedDiamondTest
    ) else (
        echo Тест вложенного ромбовидного наследования пропущен - файлы не найдены.
    )
)

echo Тесты выполнены! 