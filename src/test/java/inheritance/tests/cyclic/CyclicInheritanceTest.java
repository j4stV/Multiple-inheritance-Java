package inheritance.tests.cyclic;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Test for checking cyclic inheritance (A -> B -> C -> A)
 * Verifies the correct processing of cyclic dependencies
 * and proper detection of cycles in topological sorting
 */
public class CyclicInheritanceTest {
    
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
    public void testCyclicInheritance() {
        try {
            // Attempt to create an instance of class A with cyclic dependency
            ClassA instanceA = MixinFactory.createInstance(ClassA.class);
            fail("An error about cyclic dependency should be thrown");
        } catch (RuntimeException e) {
            // Check that the exception has the correct message
            assertTrue("Error message should contain information about cyclic dependency", 
                    e.getMessage().contains("cycle") || e.getMessage().contains("cyclic"));
        }
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