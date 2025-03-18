package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Класс B - зависит от класса A
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
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 