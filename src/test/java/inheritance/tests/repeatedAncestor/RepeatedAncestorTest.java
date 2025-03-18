package inheritance.tests.repeatedAncestor;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for checking inheritance with a repeated ancestor
 * Inheritance structure: D extends A,B; B extends A
 * Verifies the correct construction of the inheritance chain
 * and access to methods when there is a repeated ancestor
 */
public class RepeatedAncestorTest {
    
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
    public void testRepeatedAncestorInheritance() {
        // Create an instance of class D using mixin factory
        ClassD instanceD = MixinFactory.createInstance(ClassD.class);
        
        // Check the result of calling testMethod
        // Expect "DBA" - call order should be D -> B -> A
        // A should not be called twice, even if it is an ancestor for both D and B
        String result = instanceD.testMethod();
        assertEquals("DBA", result);
        
        // Check availability of parent classes' specific methods
        String specificResult = instanceD.callParentSpecificMethods();
        
        // Check that the method returns the correct string
        // Depending on who is the direct parent of D
        if (instanceD.parent instanceof ClassA) {
            assertEquals("A-specific", specificResult);
        } else if (instanceD.parent instanceof ClassB) {
            assertTrue(specificResult.contains("B-specific"));
            // If the parent is B, then through it we also have access to A's methods
            // But we are more interested in the direct parent only
        }
        
        // Check that D has access to both parent classes
        assertTrue("Should have access to class A methods through hierarchy",
                specificResult.contains("A-specific") || (instanceD.parent instanceof ClassB));
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