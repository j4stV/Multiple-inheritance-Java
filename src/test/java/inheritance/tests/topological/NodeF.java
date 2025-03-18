package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Class F - depends on classes C and E, forming a complex inheritance graph
 */
@Mixin({NodeC.class, NodeE.class})
public class NodeF extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private final int order;
    
    public NodeF() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "F(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Resets the node visit order counter
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 