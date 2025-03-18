package inheritance.tests.topological;

/**
 * Class Z closes the cyclic dependency:
 * Z -> Y -> X -> Z (cyclic dependency)
 */
public class CyclicNodeZ extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private int order;
    
    public CyclicNodeZ() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "Z(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Resets the node visit order counter
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 