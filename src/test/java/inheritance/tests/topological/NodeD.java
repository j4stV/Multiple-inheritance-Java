package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Класс D - зависит от классов B и C, образуя ромбовидное наследование
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
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 