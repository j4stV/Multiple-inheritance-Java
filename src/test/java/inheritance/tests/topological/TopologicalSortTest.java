package inheritance.tests.topological;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for verifying the topological sorting algorithm
 * in the multiple inheritance factory
 */
public class TopologicalSortTest {
    
    @Before
    public void setUp() {
        // Disable debug output
        MixinFactory.setDebugEnabled(false);
        // Clear cache before each test
        MixinFactory.clearCache();
        
        // Reset visit counters for all nodes
        NodeA.resetVisitOrder();
        NodeB.resetVisitOrder();
        NodeC.resetVisitOrder();
        NodeD.resetVisitOrder();
        NodeE.resetVisitOrder();
        NodeF.resetVisitOrder();
    }
    
    @After
    public void tearDown() {
        // Clear cache after each test
        MixinFactory.clearCache();
    }
    
    /**
     * Verifies a simple linear inheritance chain (A <- B)
     */
    @Test
    public void testSimpleLinearInheritance() {
        NodeB nodeB = MixinFactory.createInstance(NodeB.class);
        
        // Order should be B -> A
        String result = nodeB.getTopologicalOrder();
        System.out.println("Linear inheritance order: " + result);
        
        assertTrue("B should be before A", result.startsWith("B"));
        assertTrue("A should be after B", result.contains("-> A"));
    }
    
    /**
     * Verifies simple diamond inheritance (A <- B, A <- C, B,C <- D)
     */
    @Test
    public void testDiamondInheritance() {
        NodeD nodeD = MixinFactory.createInstance(NodeD.class);
        
        // Order should be D -> B/C -> A
        String result = nodeD.getTopologicalOrder();
        System.out.println("Diamond inheritance order: " + result);
        
        assertTrue("D should be first", result.startsWith("D"));
        
        // Check that A appears only once (no duplication)
        int countA = result.split("A\\(").length - 1;
        assertEquals("A should appear only once", 1, countA);
        
        // Check overall structure
        assertTrue("Order should be D -> (B or C) -> (C or B) -> A", 
                result.matches("D\\(\\d+\\) -> [BC]\\(\\d+\\) -> [BC]\\(\\d+\\) -> A\\(\\d+\\)"));
    }
    
    /**
     * Verifies a complex inheritance graph with multiple paths
     */
    @Test
    public void testComplexGraph() {
        NodeF nodeF = MixinFactory.createInstance(NodeF.class);
        
        // Order should be F -> C/E -> B -> A
        String result = nodeF.getTopologicalOrder();
        System.out.println("Complex graph inheritance order: " + result);
        
        assertTrue("F should be first", result.startsWith("F"));
        
        // Check that A and B appear only once (no duplication)
        int countA = result.split("A\\(").length - 1;
        int countB = result.split("B\\(").length - 1;
        
        assertEquals("A should appear only once", 1, countA);
        assertEquals("B should appear only once", 1, countB);
        
        // Check overall structure - one of possible paths
        assertTrue("Inheritance structure is incorrect", 
                result.contains("F(") && result.contains("E(") && 
                result.contains("C(") && result.contains("B(") && 
                result.contains("A("));
    }
    
    /**
     * Verifies different entry points into the graph which should produce
     * topologically equivalent results
     */
    @Test
    public void testDifferentEntryPoints() {
        // Reset counters
        NodeA.resetVisitOrder();
        NodeB.resetVisitOrder();
        NodeC.resetVisitOrder();
        
        // Create instances from different entry points
        NodeB nodeB = MixinFactory.createInstance(NodeB.class);
        String resultB = nodeB.getTopologicalOrder();
        
        // Clear cache and reset counters
        MixinFactory.clearCache();
        NodeA.resetVisitOrder();
        NodeB.resetVisitOrder();
        NodeC.resetVisitOrder();
        
        NodeC nodeC = MixinFactory.createInstance(NodeC.class);
        String resultC = nodeC.getTopologicalOrder();
        
        System.out.println("Entry B order: " + resultB);
        System.out.println("Entry C order: " + resultC);
        
        // Check that both paths end with A and have correct structure
        assertTrue("Path B should end with A", resultB.endsWith("A(1)"));
        assertTrue("Path C should end with A", resultC.endsWith("A(1)"));
        
        assertTrue("Path B has incorrect structure", resultB.matches("B\\(\\d+\\) -> A\\(\\d+\\)"));
        assertTrue("Path C has incorrect structure", resultC.matches("C\\(\\d+\\) -> A\\(\\d+\\)"));
    }
} 