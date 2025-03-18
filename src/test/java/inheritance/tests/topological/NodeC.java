package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Класс C - зависит от класса A
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
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 