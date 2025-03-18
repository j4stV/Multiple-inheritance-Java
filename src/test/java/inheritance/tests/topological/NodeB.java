package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Class B - depends on class A
 */
@Mixin(NodeA.class)
public class NodeB extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private final int order;
    
    public NodeB() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "B(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Resets the node visit order counter
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 