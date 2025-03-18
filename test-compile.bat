@echo off
echo === Multiple inheritance test compilation and execution script ===

REM Compilation and execution parameters
set JUNIT_PATH=lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar
set TEST_CLASSES=bin\test
set MAIN_CLASSES=bin

REM Create test directories if they don't exist
if not exist %TEST_CLASSES% mkdir %TEST_CLASSES%
if not exist lib mkdir lib

REM Check if JUnit libraries exist, if not - download them
if not exist lib\junit-4.13.2.jar (
    echo Downloading JUnit libraries...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar', 'lib\junit-4.13.2.jar')"
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar', 'lib\hamcrest-core-1.3.jar')"
)

REM Clean directories before compilation
if exist %TEST_CLASSES% rmdir /s /q %TEST_CLASSES%
mkdir %TEST_CLASSES%

REM Compile main project (if not already compiled)
echo Checking main project compilation...
if not exist %MAIN_CLASSES%\inheritance\factory\MixinFactory.class (
    echo Main project not compiled, starting compilation...
    call compile.bat
    if errorlevel 1 (
        echo Error compiling main project
        exit /b 1
    )
)

REM Select test to run
set TEST_TO_RUN=%1
if "%TEST_TO_RUN%"=="" (
    set TEST_TO_RUN=linear
)

echo Running tests for %TEST_TO_RUN% inheritance...

if "%TEST_TO_RUN%"=="linear" (
    REM Compile and run linear inheritance tests
    echo Compiling interface for linear inheritance...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/TestLinearInterface.java
    if errorlevel 1 (
        echo Error compiling interface for linear inheritance
        exit /b 1
    )
    
    echo Compiling classes for linear inheritance...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/ClassA.java
    if errorlevel 1 (
        echo Error compiling ClassA
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/ClassB.java
    if errorlevel 1 (
        echo Error compiling ClassB
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/ClassC.java
    if errorlevel 1 (
        echo Error compiling ClassC
        exit /b 1
    )
    
    echo Compiling linear inheritance test...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/LinearInheritanceTest.java
    if errorlevel 1 (
        echo Error compiling linear inheritance test
        exit /b 1
    )
    
    echo Running linear inheritance test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.linear.LinearInheritanceTest
) else if "%TEST_TO_RUN%"=="diamond" (
    REM Compile and run diamond inheritance tests
    echo Compiling interface for diamond inheritance...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/DiamondInterface.java
    if errorlevel 1 (
        echo Error compiling interface for diamond inheritance
        exit /b 1
    )
    
    echo Compiling classes for diamond inheritance...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassA.java
    if errorlevel 1 (
        echo Error compiling ClassA
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassB.java
    if errorlevel 1 (
        echo Error compiling ClassB
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassC.java
    if errorlevel 1 (
        echo Error compiling ClassC
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/ClassD.java
    if errorlevel 1 (
        echo Error compiling ClassD
        exit /b 1
    )
    
    echo Compiling diamond inheritance test...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/DiamondInheritanceTest.java
    if errorlevel 1 (
        echo Error compiling diamond inheritance test
        exit /b 1
    )
    
    echo Running diamond inheritance test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.diamond.DiamondInheritanceTest
) else if "%TEST_TO_RUN%"=="cyclic" (
    REM Compile and run cyclic inheritance tests
    echo Compiling interface for cyclic inheritance...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/CyclicInterface.java
    if errorlevel 1 (
        echo Error compiling interface for cyclic inheritance
        exit /b 1
    )
    
    echo Compiling classes for cyclic inheritance...
    REM Compile all classes in one command to avoid issues with cyclic dependencies
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/ClassA.java src/test/java/inheritance/tests/cyclic/ClassB.java src/test/java/inheritance/tests/cyclic/ClassC.java
    if errorlevel 1 (
        echo Error compiling classes for cyclic inheritance
        exit /b 1
    )
    
    echo Compiling cyclic inheritance test...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/CyclicInheritanceTest.java
    if errorlevel 1 (
        echo Error compiling cyclic inheritance test
        exit /b 1
    )
    
    echo Running cyclic inheritance test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.cyclic.CyclicInheritanceTest
) else if "%TEST_TO_RUN%"=="deepChain" (
    REM Compile and run deep chain inheritance tests
    if exist src\test\java\inheritance\tests\deepChain\*.java (
        echo Compiling classes for deep chain inheritance...
        
        REM Find all Java files in directory
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\deepChain\*.java) do (
            set "java_files=java_files %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Error compiling classes for deep chain inheritance
            exit /b 1
        )
        
        echo Running deep chain inheritance test...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.deepChain.DeepChainInheritanceTest
    ) else (
        echo Deep chain inheritance test skipped - files not found.
    )
) else if "%TEST_TO_RUN%"=="repeatedAncestor" (
    REM Compile and run repeated ancestor tests
    echo Compiling interface for repeated ancestor...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/repeatedAncestor/RepeatedAncestorInterface.java
    if errorlevel 1 (
        echo Error compiling interface for repeated ancestor
        exit /b 1
    )
    
    echo Compiling classes for repeated ancestor...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/repeatedAncestor/ClassA.java
    if errorlevel 1 (
        echo Error compiling ClassA
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/repeatedAncestor/ClassB.java
    if errorlevel 1 (
        echo Error compiling ClassB
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/repeatedAncestor/ClassD.java
    if errorlevel 1 (
        echo Error compiling ClassD
        exit /b 1
    )
    
    echo Compiling repeated ancestor test...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/repeatedAncestor/RepeatedAncestorTest.java
    if errorlevel 1 (
        echo Error compiling repeated ancestor test
        exit /b 1
    )
    
    echo Running repeated ancestor test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.repeatedAncestor.RepeatedAncestorTest
) else if "%TEST_TO_RUN%"=="nestedDiamond" (
    REM Compile and run nested diamond inheritance tests
    if exist src\test\java\inheritance\tests\nestedDiamond\*.java (
        echo Compiling classes for nested diamond inheritance...
        
        REM Find all Java files in directory
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\nestedDiamond\*.java) do (
            set "java_files=java_files %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Error compiling classes for nested diamond inheritance
            exit /b 1
        )
        
        echo Running nested diamond inheritance test...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.nestedDiamond.NestedDiamondTest
    ) else (
        echo Nested diamond inheritance test skipped - files not found.
    )
) else if "%TEST_TO_RUN%"=="generic" (
    REM Compile and run generics tests
    echo Compiling interface and classes for generics...
    
    REM Compile generic interface
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/generic/GenericInterface.java
    if errorlevel 1 (
        echo Error compiling generic interface
        exit /b 1
    )
    
    REM Compile generic classes
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/generic/StringContainer.java
    if errorlevel 1 (
        echo Error compiling StringContainer
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/generic/EnhancedStringContainer.java
    if errorlevel 1 (
        echo Error compiling EnhancedStringContainer
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/generic/IntegerContainer.java
    if errorlevel 1 (
        echo Error compiling IntegerContainer
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/generic/MixedTypeContainer.java
    if errorlevel 1 (
        echo Error compiling MixedTypeContainer
        exit /b 1
    )
    
    echo Compiling generic test...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/generic/GenericInheritanceTest.java
    if errorlevel 1 (
        echo Error compiling generic test
        exit /b 1
    )
    
    echo Running generic test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.generic.GenericInheritanceTest
) else if "%TEST_TO_RUN%"=="topological" (
    REM Compile and run topological sorting tests
    echo Compiling interface and classes for topological sorting...
    
    REM Compile interface
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/TopologicalInterface.java
    if errorlevel 1 (
        echo Error compiling interface for topological sorting
        exit /b 1
    )
    
    REM Compile base classes
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/NodeA.java
    if errorlevel 1 (
        echo Error compiling NodeA
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/NodeB.java
    if errorlevel 1 (
        echo Error compiling NodeB
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/NodeC.java
    if errorlevel 1 (
        echo Error compiling NodeC
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/NodeD.java
    if errorlevel 1 (
        echo Error compiling NodeD
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/NodeE.java
    if errorlevel 1 (
        echo Error compiling NodeE
        exit /b 1
    )
    
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/NodeF.java
    if errorlevel 1 (
        echo Error compiling NodeF
        exit /b 1
    )
    
    echo Compiling topological sorting test...
    javac -d %TEST_CLASSES% -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/TopologicalSortTest.java
    if errorlevel 1 (
        echo Error compiling topological sorting test
        exit /b 1
    )
    
    echo Running topological sorting test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.topological.TopologicalSortTest
) else if "%TEST_TO_RUN%"=="all" (
    REM Run all tests
    
    REM Linear inheritance
    echo Compiling classes for linear inheritance...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/linear/*.java
    if errorlevel 1 (
        echo Error compiling classes for linear inheritance
        exit /b 1
    )
    
    echo Running linear inheritance test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.linear.LinearInheritanceTest
    
    REM Diamond inheritance
    echo Compiling classes for diamond inheritance...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/diamond/*.java
    if errorlevel 1 (
        echo Error compiling classes for diamond inheritance
        exit /b 1
    )
    
    echo Running diamond inheritance test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.diamond.DiamondInheritanceTest
    
    REM Cyclic inheritance
    echo Compiling classes for cyclic inheritance...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/cyclic/*.java
    if errorlevel 1 (
        echo Error compiling classes for cyclic inheritance
        exit /b 1
    )
    
    echo Running cyclic inheritance test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.cyclic.CyclicInheritanceTest
    
    REM Repeated ancestor
    echo Compiling classes for repeated ancestor...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/repeatedAncestor/*.java
    if errorlevel 1 (
        echo Error compiling classes for repeated ancestor
        exit /b 1
    )
    
    echo Running repeated ancestor test...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.repeatedAncestor.RepeatedAncestorTest
    
    REM Deep chain inheritance
    if exist src\test\java\inheritance\tests\deepChain\*.java (
        echo Compiling classes for deep chain inheritance...
        
        REM Find all Java files in directory
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\deepChain\*.java) do (
            set "java_files=java_files %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Error compiling classes for deep chain inheritance
            exit /b 1
        )
        
        echo Running deep chain inheritance test...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.deepChain.DeepChainInheritanceTest
    ) else (
        echo Deep chain inheritance test skipped - files not found.
    )
    
    REM Nested diamond inheritance
    if exist src\test\java\inheritance\tests\nestedDiamond\*.java (
        echo Compiling classes for nested diamond inheritance...
        
        REM Find all Java files in directory
        setlocal enabledelayedexpansion
        set "java_files="
        for %%f in (src\test\java\inheritance\tests\nestedDiamond\*.java) do (
            set "java_files=java_files %%f"
        )
        
        javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% %java_files%
        if errorlevel 1 (
            echo Error compiling classes for nested diamond inheritance
            exit /b 1
        )
        
        echo Running nested diamond inheritance test...
        java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.nestedDiamond.NestedDiamondTest
    ) else (
        echo Nested diamond inheritance test skipped - files not found.
    )
    
    REM Generic tests
    echo Compiling classes for generics...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/generic/*.java
    if errorlevel 1 (
        echo Error compiling classes for generics
        exit /b 1
    )
    
    echo Running generic tests...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.generic.GenericInheritanceTest
    
    REM Topological sorting tests
    echo Compiling classes for topological sorting...
    javac -d %TEST_CLASSES% -cp %MAIN_CLASSES%;%JUNIT_PATH% src/test/java/inheritance/tests/topological/*.java
    if errorlevel 1 (
        echo Error compiling classes for topological sorting
        exit /b 1
    )
    
    echo Running topological sorting tests...
    java -cp %TEST_CLASSES%;%MAIN_CLASSES%;%JUNIT_PATH% org.junit.runner.JUnitCore inheritance.tests.topological.TopologicalSortTest
)

echo Tests completed 