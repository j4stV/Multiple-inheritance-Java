package inheritance.tests.topological;

/**
 * Класс Y участвует в циклической зависимости:
 * Y -> X -> Z -> Y (циклическая зависимость)
 */
public class CyclicNodeY extends TopologicalInterfaceRoot {
    private static int visitOrder = 0;
    private int order;
    
    public CyclicNodeY() {
        this.order = ++visitOrder;
    }
    
    @Override
    public String getTopologicalOrder() {
        return "Y(" + order + ") -> " + nextGetTopologicalOrder();
    }
    
    /**
     * Сбрасывает счетчик порядка посещения узлов
     */
    public static void resetVisitOrder() {
        visitOrder = 0;
    }
} 