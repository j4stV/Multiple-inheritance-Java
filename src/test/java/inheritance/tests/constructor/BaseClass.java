package inheritance.tests.constructor;

/**
 * Base class with parameterized constructor
 */
public class BaseClass extends ConstructorInterfaceRoot {
    protected String value;
    
    /**
     * Default constructor
     */
    public BaseClass() {
        this.value = "default";
    }
    
    /**
     * Constructor with parameter
     * @param value value to store
     */
    public BaseClass(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return value;
    }
} 