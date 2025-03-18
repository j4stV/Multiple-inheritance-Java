package inheritance.tests.topological;

/**
 * Класс Z замыкает циклическую зависимость:
 * Z -> Y -> X -> Z (циклическая зависимость)
 */
public class CyclicNodeZ extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private int order;
    
    public CyclicNodeZ() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "Z(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 