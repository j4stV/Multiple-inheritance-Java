package inheritance.tests.diamond;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for checking diamond inheritance (A -> B,C -> D)
 * Verifies the correct construction of the inheritance chain
 * and accessibility of methods from different inheritance branches
 */
public class DiamondInheritanceTest {
    
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
    public void testDiamondInheritance() {
        // Create an instance of class D using mixin factory
        ClassD instanceD = MixinFactory.createInstance(ClassD.class);
        
        // Check the result of calling testMethod
        // Expect "DBCA" as a result of topological sorting
        String result = instanceD.testMethod();
        assertEquals("DBCA", result);
        
        // Check availability of specific methods of B
        String specificResult = instanceD.callParentSpecificMethods();
        assertEquals("B-specific", specificResult);
        
        // Check parent relationships through instanceof
        assertTrue("Parent of D should be an instance of B", instanceD.parent instanceof ClassB);
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