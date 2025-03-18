package inheritance.tests.topological;

/**
 * Class A - root node in the inheritance graph
 */
public class NodeA extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private final int order;
    
    public NodeA() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "A(" + order + ")";
    }
    
    /**
     * Resets the node visit order counter
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 