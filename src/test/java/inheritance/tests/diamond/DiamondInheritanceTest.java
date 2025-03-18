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
        // Enable debug output
        MixinFactory.setDebugEnabled(true);
        // No longer clearing cache here
    }
    
    @After
    public void tearDown() {
        // Delete generated files after tests
        cleanupGeneratedFiles();
        // Clear caches after all tests
        MixinFactory.clearCache();
    }
    
    @Test
    public void testDiamondInheritance() {
        // Clear cache before first test
        MixinFactory.clearCache();
        
        // Create an instance of class D using mixin factory
        ClassD instanceD = MixinFactory.createInstance(ClassD.class);
        
        // Check the result of calling testMethod
        // Expect "DCBA" as a result of topological sorting
        String result = instanceD.testMethod();
        assertEquals("DCBA", result);
        
        // Check availability of specific methods of B
        String specificResult = instanceD.callParentSpecificMethods();
        assertEquals("B-specific", specificResult);
        
        // Check parent relationships through instanceof
        assertTrue("Parent of D should be an instance of C", instanceD.parent instanceof ClassC);
    }
    
    @Test
    public void testDiamondInheritanceWithCaching() {
        // Do not clear cache here to test caching
        System.out.println("\n=== Testing cached inheritance structure ===");
        
        // Create an instance of class D using mixin factory - should use cached results
        ClassD instanceD = MixinFactory.createInstance(ClassD.class);
        
        // Check the result of calling testMethod
        String result = instanceD.testMethod();
        assertEquals("DCBA", result);
    }
    
    @Test
    public void testSingleMethodCaching() {
        // Clearly demonstrate caching in a single test method
        System.out.println("\n=== Testing caching within a single test method ===");
        
        // 1. Clear all caches
        MixinFactory.clearCache();
        
        // 2. First call should build the inheritance structure
        System.out.println("First call - should build inheritance structure:");
        ClassD instanceD1 = MixinFactory.createInstance(ClassD.class);
        String result1 = instanceD1.testMethod();
        assertEquals("DCBA", result1);
        
        // 3. Second call should use cached structure
        System.out.println("\nSecond call - should use cached structure:");
        ClassD instanceD2 = MixinFactory.createInstance(ClassD.class);
        String result2 = instanceD2.testMethod();
        assertEquals("DCBA", result2);
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