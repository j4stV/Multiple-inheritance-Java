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

elif [ "$TEST_TO_RUN" = "diamond" ]; then
    # Compile and run diamond inheritance tests
    echo "Compiling interface for diamond inheritance..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/diamond/DiamondInterface.java
    if [ $? -ne 0 ]; then
        echo "Error compiling interface for diamond inheritance"
        exit 1
    fi
    
    echo "Compiling classes for diamond inheritance..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/diamond/ClassA.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassA"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/diamond/ClassB.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassB"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/diamond/ClassC.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassC"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/diamond/ClassD.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassD"
        exit 1
    fi
    
    echo "Compiling diamond inheritance test..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/diamond/DiamondInheritanceTest.java
    if [ $? -ne 0 ]; then
        echo "Error compiling diamond inheritance test"
        exit 1
    fi
    
    echo "Running diamond inheritance test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.diamond.DiamondInheritanceTest

elif [ "$TEST_TO_RUN" = "constructor" ]; then
    # Compile and run constructor tests
    echo "Compiling interface for constructor tests..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/constructor/ConstructorInterface.java
    if [ $? -ne 0 ]; then
        echo "Error compiling interface for constructor tests"
        exit 1
    fi
    
    echo "Compiling classes for constructor tests..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/constructor/BaseClass.java
    if [ $? -ne 0 ]; then
        echo "Error compiling BaseClass"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/constructor/ChildClass.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ChildClass"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/constructor/BadChildClass.java
    if [ $? -ne 0 ]; then
        echo "Error compiling BadChildClass"
        exit 1
    fi
    
    echo "Compiling constructor test..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/constructor/ConstructorTest.java
    if [ $? -ne 0 ]; then
        echo "Error compiling constructor test"
        exit 1
    fi
    
    echo "Running constructor test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.constructor.ConstructorTest

elif [ "$TEST_TO_RUN" = "cyclic" ]; then
    # Compile and run cyclic inheritance tests
    echo "Compiling interface for cyclic inheritance..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/cyclic/CyclicInterface.java
    if [ $? -ne 0 ]; then
        echo "Error compiling interface for cyclic inheritance"
        exit 1
    fi
    
    echo "Compiling classes for cyclic inheritance..."
    # Compile all classes in one command to avoid issues with cyclic dependencies
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/cyclic/ClassA.java src/test/java/inheritance/tests/cyclic/ClassB.java src/test/java/inheritance/tests/cyclic/ClassC.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for cyclic inheritance"
        exit 1
    fi
    
    echo "Compiling cyclic inheritance test..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/cyclic/CyclicInheritanceTest.java
    if [ $? -ne 0 ]; then
        echo "Error compiling cyclic inheritance test"
        exit 1
    fi
    
    echo "Running cyclic inheritance test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.cyclic.CyclicInheritanceTest

elif [ "$TEST_TO_RUN" = "deepChain" ]; then
    # Compile and run deep chain inheritance tests
    if [ -d "src/test/java/inheritance/tests/deepChain" ]; then
        echo "Compiling classes for deep chain inheritance..."
        
        # Find all Java files in directory
        JAVA_FILES=$(find src/test/java/inheritance/tests/deepChain -name "*.java")
        
        javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH $JAVA_FILES
        if [ $? -ne 0 ]; then
            echo "Error compiling classes for deep chain inheritance"
            exit 1
        fi
        
        echo "Running deep chain inheritance test..."
        java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.deepChain.DeepChainInheritanceTest
    else
        echo "Deep chain inheritance test skipped - files not found."
    fi

elif [ "$TEST_TO_RUN" = "repeatedAncestor" ]; then
    # Compile and run repeated ancestor tests
    echo "Compiling interface for repeated ancestor..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/repeatedAncestor/RepeatedAncestorInterface.java
    if [ $? -ne 0 ]; then
        echo "Error compiling interface for repeated ancestor"
        exit 1
    fi
    
    echo "Compiling classes for repeated ancestor..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/repeatedAncestor/ClassA.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassA"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/repeatedAncestor/ClassB.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassB"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/repeatedAncestor/ClassD.java
    if [ $? -ne 0 ]; then
        echo "Error compiling ClassD"
        exit 1
    fi
    
    echo "Compiling repeated ancestor test..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/repeatedAncestor/RepeatedAncestorTest.java
    if [ $? -ne 0 ]; then
        echo "Error compiling repeated ancestor test"
        exit 1
    fi
    
    echo "Running repeated ancestor test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.repeatedAncestor.RepeatedAncestorTest

elif [ "$TEST_TO_RUN" = "nestedDiamond" ]; then
    # Compile and run nested diamond inheritance tests
    if [ -d "src/test/java/inheritance/tests/nestedDiamond" ]; then
        echo "Compiling classes for nested diamond inheritance..."
        
        # Find all Java files in directory
        JAVA_FILES=$(find src/test/java/inheritance/tests/nestedDiamond -name "*.java")
        
        javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH $JAVA_FILES
        if [ $? -ne 0 ]; then
            echo "Error compiling classes for nested diamond inheritance"
            exit 1
        fi
        
        echo "Running nested diamond inheritance test..."
        java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.nestedDiamond.NestedDiamondTest
    else
        echo "Nested diamond inheritance test skipped - files not found."
    fi

elif [ "$TEST_TO_RUN" = "generic" ]; then
    # Compile and run generics tests
    echo "Compiling interface and classes for generics..."
    
    # Compile generic interface
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/generic/GenericInterface.java
    if [ $? -ne 0 ]; then
        echo "Error compiling generic interface"
        exit 1
    fi
    
    # Compile generic classes
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/generic/StringContainer.java
    if [ $? -ne 0 ]; then
        echo "Error compiling StringContainer"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/generic/EnhancedStringContainer.java
    if [ $? -ne 0 ]; then
        echo "Error compiling EnhancedStringContainer"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/generic/IntegerContainer.java
    if [ $? -ne 0 ]; then
        echo "Error compiling IntegerContainer"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/generic/MixedTypeContainer.java
    if [ $? -ne 0 ]; then
        echo "Error compiling MixedTypeContainer"
        exit 1
    fi
    
    echo "Compiling generic test..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/generic/GenericInheritanceTest.java
    if [ $? -ne 0 ]; then
        echo "Error compiling generic test"
        exit 1
    fi
    
    echo "Running generic test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.generic.GenericInheritanceTest

elif [ "$TEST_TO_RUN" = "topological" ]; then
    # Compile and run topological sorting tests
    echo "Compiling interface and classes for topological sorting..."
    
    # Compile interface
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/TopologicalInterface.java
    if [ $? -ne 0 ]; then
        echo "Error compiling interface for topological sorting"
        exit 1
    fi
    
    # Compile base classes
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/NodeA.java
    if [ $? -ne 0 ]; then
        echo "Error compiling NodeA"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/NodeB.java
    if [ $? -ne 0 ]; then
        echo "Error compiling NodeB"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/NodeC.java
    if [ $? -ne 0 ]; then
        echo "Error compiling NodeC"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/NodeD.java
    if [ $? -ne 0 ]; then
        echo "Error compiling NodeD"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/NodeE.java
    if [ $? -ne 0 ]; then
        echo "Error compiling NodeE"
        exit 1
    fi
    
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/NodeF.java
    if [ $? -ne 0 ]; then
        echo "Error compiling NodeF"
        exit 1
    fi
    
    echo "Compiling topological sorting test..."
    javac -d $TEST_CLASSES -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/TopologicalSortTest.java
    if [ $? -ne 0 ]; then
        echo "Error compiling topological sorting test"
        exit 1
    fi
    
    echo "Running topological sorting test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.topological.TopologicalSortTest

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
    
    # Constructor tests
    echo "Compiling classes for constructor tests..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/constructor/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for constructor tests"
        exit 1
    fi
    
    echo "Running constructor test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.constructor.ConstructorTest
    
    # Cyclic inheritance
    echo "Compiling classes for cyclic inheritance..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/cyclic/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for cyclic inheritance"
        exit 1
    fi
    
    echo "Running cyclic inheritance test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.cyclic.CyclicInheritanceTest
    
    # Repeated ancestor tests
    echo "Compiling classes for repeated ancestor..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/repeatedAncestor/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for repeated ancestor"
        exit 1
    fi
    
    echo "Running repeated ancestor test..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.repeatedAncestor.RepeatedAncestorTest
    
    # Deep chain inheritance tests
    if [ -d "src/test/java/inheritance/tests/deepChain" ]; then
        echo "Compiling classes for deep chain inheritance..."
        
        # Find all Java files in directory
        JAVA_FILES=$(find src/test/java/inheritance/tests/deepChain -name "*.java")
        
        javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH $JAVA_FILES
        if [ $? -ne 0 ]; then
            echo "Error compiling classes for deep chain inheritance"
            exit 1
        fi
        
        echo "Running deep chain inheritance test..."
        java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.deepChain.DeepChainInheritanceTest
    else
        echo "Deep chain inheritance test skipped - files not found."
    fi
    
    # Nested diamond inheritance tests
    if [ -d "src/test/java/inheritance/tests/nestedDiamond" ]; then
        echo "Compiling classes for nested diamond inheritance..."
        
        # Find all Java files in directory
        JAVA_FILES=$(find src/test/java/inheritance/tests/nestedDiamond -name "*.java")
        
        javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH $JAVA_FILES
        if [ $? -ne 0 ]; then
            echo "Error compiling classes for nested diamond inheritance"
            exit 1
        fi
        
        echo "Running nested diamond inheritance test..."
        java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.nestedDiamond.NestedDiamondTest
    else
        echo "Nested diamond inheritance test skipped - files not found."
    fi
    
    # Generic tests
    echo "Compiling classes for generics..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/generic/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for generics"
        exit 1
    fi
    
    echo "Running generic tests..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.generic.GenericInheritanceTest
    
    # Topological sorting tests
    echo "Compiling classes for topological sorting..."
    javac -d $TEST_CLASSES -cp $MAIN_CLASSES:$JUNIT_PATH src/test/java/inheritance/tests/topological/*.java
    if [ $? -ne 0 ]; then
        echo "Error compiling classes for topological sorting"
        exit 1
    fi
    
    echo "Running topological sorting tests..."
    java -cp $TEST_CLASSES:$MAIN_CLASSES:$JUNIT_PATH org.junit.runner.JUnitCore inheritance.tests.topological.TopologicalSortTest
fi

echo "Tests completed" 