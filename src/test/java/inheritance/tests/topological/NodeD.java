package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Class D - depends on classes B and C, forming a diamond inheritance
 */
@Mixin({NodeB.class, NodeC.class})
public class NodeD extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private final int order;
    
    public NodeD() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "D(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Resets the node visit order counter
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 