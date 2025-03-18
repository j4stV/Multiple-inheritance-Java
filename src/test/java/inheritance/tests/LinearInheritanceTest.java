package inheritance.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import inheritance.annotations.Root;
import inheritance.annotations.Mixin;
import inheritance.factory.MixinFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Test for checking linear inheritance chain (A → B → C)
 */
public class LinearInheritanceTest {
    
    private static final String GENERATED_DIR = "build/generated/sources/annotationProcessor/java/main";
    private List<String> methodCallOrder;

    @Root
    public interface TestLinearInterface {
        void testMethod();
    }

    public class A extends TestLinearInterfaceRoot {
        @Override
        public void testMethod() {
            methodCallOrder.add("A");
            nextTestMethod();
        }
    }

    @Mixin(A.class)
    public class B extends TestLinearInterfaceRoot {
        @Override
        public void testMethod() {
            methodCallOrder.add("B");
            nextTestMethod();
        }
    }

    @Mixin(B.class)
    public class C extends TestLinearInterfaceRoot {
        @Override
        public void testMethod() {
            methodCallOrder.add("C");
            nextTestMethod();
        }
    }

    @Before
    public void setUp() {
        methodCallOrder = new ArrayList<>();
        MixinFactory.setDebugEnabled(false); // Disable debug output
    }

    @After
    public void tearDown() {
        // Clean up generated files
        deleteGeneratedFiles();
    }

    @Test
    public void testLinearInheritance() {
        // Create an instance of class C with linear inheritance C → B → A
        C c = MixinFactory.createInstance(C.class);
        
        // Call the method that should go through the chain
        c.testMethod();
        
        // Check the call order
        assertEquals(3, methodCallOrder.size());
        assertEquals("C", methodCallOrder.get(0));
        assertEquals("B", methodCallOrder.get(1));
        assertEquals("A", methodCallOrder.get(2));
    }

    /**
     * Deletes generated files after test execution
     */
    private void deleteGeneratedFiles() {
        File generatedDir = new File(GENERATED_DIR);
        if (generatedDir.exists()) {
            deleteDirectory(generatedDir);
        }
    }

    /**
     * Recursively deletes a directory and all its contents
     */
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
} 