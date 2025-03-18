package inheritance.tests.topological;

import inheritance.annotations.Mixin;

/**
 * Класс F - зависит от классов C и E, образуя сложный граф наследования
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
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 