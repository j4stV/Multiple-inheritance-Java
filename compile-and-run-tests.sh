#!/bin/bash
echo "=== Multiple inheritance test compilation and execution script ==="

# Compilation and execution parameters
JUNIT_PATH="lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar"
TEST_CLASSES="bin/test"
MAIN_CLASSES="bin"

# Create test directories if they don't exist
mkdir -p $TEST_CLASSES
mkdir -p lib

# Check for JUnit libraries, download if they don't exist
if [ ! -f lib/junit-4.13.2.jar ]; then
    echo "Downloading JUnit libraries..."
    wget -O lib/junit-4.13.2.jar https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
    wget -O lib/hamcrest-core-1.3.jar https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
fi

# Clean directories before compilation
rm -rf $TEST_CLASSES
mkdir -p $TEST_CLASSES

# Compile main project (if not already compiled)
echo "Checking main project compilation..."
if [ ! -f $MAIN_CLASSES/inheritance/factory/MixinFactory.class ]; then
    echo "Main project not compiled, starting compilation..."
    ./compile.sh
    if [ $? -ne 0 ]; then
        echo "Error compiling main project"
        exit 1
    fi
fi

# Select test to run
TEST_TO_RUN=${1:-linear}

echo "Running tests for $TEST_TO_RUN inheritance..."

if [ "$TEST_TO_RUN" = "linear" ]; then
    # Compile and run linear inheritance tests
    echo "Compiling interface for linear inheritance..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/linear/TestLinearInterface.java
    if [ $? -ne 0 ]; then
        echo "Error compiling interface for linear inheritance"
        exit 1
    fi
    
    echo "Compiling classes for linear inheritance..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/linear/ClassA.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassA"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/linear/ClassB.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassB"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/linear/ClassC.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassC"
        exit 1
    fi
    
    echo "Compiling linear inheritance test..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/linear/LinearInheritanceTest.java
    if [ $? -ne 0 ]; then
        echo "Error compiling linear inheritance test"
        exit 1
    fi
    
    echo "Running linear inheritance test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.linear.LinearInheritanceTest
elif [ "$TEST_TO_RUN" = "all" ]; then
    # Run all tests
    
    # Linear inheritance
    echo "Compiling classes for linear inheritance..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/linear/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for linear inheritance"
        exit 1
    fi
    
    echo "Running linear inheritance test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.linear.LinearInheritanceTest
    
    # Diamond inheritance
    echo "Compiling classes for diamond inheritance..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/diamond/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for diamond inheritance"
        exit 1
    fi
    
    echo "Running diamond inheritance test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.diamond.DiamondInheritanceTest
    
    # Cyclic inheritance
    echo "Compiling classes for cyclic inheritance..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/cyclic/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for cyclic inheritance"
        exit 1
    fi
    
    echo "Running cyclic inheritance test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.cyclic.CyclicInheritanceTest
fi

echo "Tests completed" 