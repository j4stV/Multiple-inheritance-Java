package inheritance.tests.topological;

/**
 * Начальный класс в циклической зависимости
 * X -> Z -> Y -> X (циклическая зависимость)
 */
public class CyclicNodeX extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private int order;
    
    public CyclicNodeX() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "X(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 