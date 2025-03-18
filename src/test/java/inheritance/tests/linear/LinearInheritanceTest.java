package inheritance.tests.linear;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Test for checking linear inheritance (A -> B -> C)
 */
public class LinearInheritanceTest {
    
    @Before
    public void setUp() {
        // Disable debug output
        MixinFactory.setDebugEnabled(false);
        // Clear instance cache before test
        MixinFactory.clearCache();
    }
    
    @After
    public void tearDown() {
        // Delete generated files after tests
        cleanupGeneratedFiles();
    }
    
    @Test
    public void testLinearInheritance() {
        // Create an instance of class C using mixin factory
        ClassC instanceC = MixinFactory.createInstance(ClassC.class);
        
        // Check the result of calling testMethod
        // Expect "CBA" - result of calling methods through chain C -> B -> A
        String result = instanceC.testMethod();
        assertEquals("CBA", result);
    }
    
    /**
     * Delete generated files
     */
    private void cleanupGeneratedFiles() {
        // Delete generated files with .class extension
        File generatedDir = new File("generated");
        if (generatedDir.exists() && generatedDir.isDirectory()) {
            File[] files = generatedDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        file.delete();
                    }
                }
            }
        }
    }
} 