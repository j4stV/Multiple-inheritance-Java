package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Class C - depends on class A
 */
@Mixin(NodeA.class)
public class NodeC extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private final int order;
    
    public NodeC() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "C(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Resets the node visit order counter
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 