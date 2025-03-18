package inheritance.tests.topological;

/**
 * Class Y participates in a cyclic dependency:
 * Y -> X -> Z -> Y (cyclic dependency)
 */
public class CyclicNodeY extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private int order;
    
    public CyclicNodeY() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "Y(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Resets the node visit order counter
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 