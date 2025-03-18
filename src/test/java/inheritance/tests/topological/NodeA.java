package inheritance.tests.topological;

/**
 * Класс A - корневой узел в графе наследования
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
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 