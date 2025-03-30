package inheritance.tests.constructor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import inheritance.factory.MixinFactory;

/**
 * Test for checking constructor behavior in multiple inheritance
 */
public class ConstructorTest {

    @Before
    public void setUp() {
        MixinFactory.setDebugEnabled(true); // Enable debug output
        MixinFactory.clearCache(); // Clear factory cache
    }

    @After
    public void tearDown() {
        MixinFactory.clearCache(); // Clear factory cache
    }

    /**
     * Test successful creation with proper constructor call
     */
    @Test
    public void testSuccessfulConstructorCall() {
        // Create instance with arguments
        ChildClass child = ConstructorInterfaceRoot.createInstance(ChildClass.class, "Hello", "World");
        
        // Check that values were passed correctly
        assertEquals("Hello World", child.getValue());
    }

    /**
     * Test that exception is thrown when nextConstructor is not called
     */
    @Test(expected = RuntimeException.class)
    public void testMissingNextConstructorCall() {
        // This should throw RuntimeException because BadChildClass doesn't call nextConstructor
        BadChildClass badChild = ConstructorInterfaceRoot.createInstance(BadChildClass.class, "Missing call");
    }

    /**
     * Test direct creation of base class with parameters
     */
    @Test
    public void testBaseClassCreation() {
        // Create base class instance directly
        BaseClass base = ConstructorInterfaceRoot.createInstance(BaseClass.class, "Test");
        
        // Check value
        assertEquals("Test", base.getValue());
    }
} 