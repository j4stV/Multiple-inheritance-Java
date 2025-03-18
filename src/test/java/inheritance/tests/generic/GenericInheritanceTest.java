package inheritance.tests.generic;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for verifying generics support in the multiple inheritance system
 */
public class GenericInheritanceTest {
    
    @Before
    public void setUp() {
        // Disable debug output
        MixinFactory.setDebugEnabled(false);
        // Clear instance cache before test
        MixinFactory.clearCache();
    }
    
    @After
    public void tearDown() {
        // Clear cache after test
        MixinFactory.clearCache();
    }
    
    /**
     * Test for checking basic generics functionality in StringContainer class
     */
    @Test
    public void testStringContainer() {
        // Create an instance of the base class
        StringContainer stringContainer = MixinFactory.createInstance(StringContainer.class);
        
        // Set and get value
        stringContainer.setValue("Generics test");
        assertEquals("Generics test", stringContainer.getValue());
        
        // Check transformation
        assertEquals("Prefix: Generics test", stringContainer.transformValue("Prefix"));
    }
    
    /**
     * Test for checking inheritance with generics
     */
    @Test
    public void testEnhancedStringContainer() {
        // Create an instance of the derived class
        EnhancedStringContainer enhancedContainer = MixinFactory.createInstance(EnhancedStringContainer.class);
        
        // Set and get value
        enhancedContainer.setValue("Generics test");
        assertEquals("Generics test", enhancedContainer.getValue());
        
        // Check transformation with conversion to uppercase
        assertEquals("PREFIX: GENERICS TEST", enhancedContainer.transformValue("Prefix"));
        
        // Check additional method
        assertEquals("generics test", enhancedContainer.getLowerCaseValue());
    }
    
    /**
     * Test for checking work with another generic type
     */
    @Test
    public void testIntegerContainer() {
        // Create an instance of class with integer type
        IntegerContainer intContainer = MixinFactory.createInstance(IntegerContainer.class);
        
        // Set and get value
        intContainer.setValue(42);
        assertEquals(Integer.valueOf(42), intContainer.getValue());
        
        // Check transformation
        assertEquals("Number (number): 42", intContainer.transformValue("Number"));
        
        // Check additional method
        assertEquals(Integer.valueOf(126), intContainer.multiply(3));
    }
    
    /**
     * Test for checking work with multiple inheritance of different types
     */
    @Test
    public void testMixedTypeContainer() {
        // Create an instance of mixed class
        MixedTypeContainer mixedContainer = MixinFactory.createInstance(MixedTypeContainer.class);
        
        // Test with string value that is not a number
        mixedContainer.setValue("Test");
        assertEquals("Test", mixedContainer.getValue());
        assertEquals(null, mixedContainer.getNumericValue());
        
        // Check transformation
        assertTrue(mixedContainer.transformValue("Prefix").startsWith("PREFIX"));
        assertFalse(mixedContainer.transformValue("Prefix").contains("Numeric value"));
        
        // Test with numeric value as string
        mixedContainer.setValue("123");
        assertEquals("123", mixedContainer.getValue());
        assertEquals(Integer.valueOf(123), mixedContainer.getNumericValue());
        
        // Check transformation with numeric value
        String transformed = mixedContainer.transformValue("Number");
        assertTrue(transformed.startsWith("NUMBER"));
        assertTrue(transformed.contains("[Numeric value: 123]"));
        
        // Check combined method
        String combined = mixedContainer.getCombinedInfo(2);
        assertTrue(combined.contains("Text: 123"));
        assertTrue(combined.contains("In lowercase: 123"));
        assertTrue(combined.contains("Numeric value: 123"));
        assertTrue(combined.contains("After multiplication by 2: 246"));
    }
}