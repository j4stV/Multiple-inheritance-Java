package inheritance.tests.topological;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Тестовый класс для проверки алгоритма топологической сортировки
 * в фабрике множественного наследования
 */
public class TopologicalSortTest {
    
    @Before
    public void setUp() {
        // Отключаем вывод отладочной информации
        MixinFactory.setDebugEnabled(false);
        // Очищаем кэш перед каждым тестом
        MixinFactory.clearCache();
        
        // Сбрасываем счетчики посещения для всех узлов
        NodeA.resetVisitOrder();
        NodeB.resetVisitOrder();
        NodeC.resetVisitOrder();
        NodeD.resetVisitOrder();
        NodeE.resetVisitOrder();
        NodeF.resetVisitOrder();
    }
    
    @After
    public void tearDown() {
        // Очищаем кэш после каждого теста
        MixinFactory.clearCache();
    }
    
    /**
     * Проверяет простую линейную цепочку наследования (A <- B)
     */
    @Test
    public void testSimpleLinearInheritance() {
        NodeB nodeB = MixinFactory.createInstance(NodeB.class);
        
        // Порядок должен быть B -> A
        String result = nodeB.getTopologicalOrder();
        System.out.println("Linear inheritance order: " + result);
        
        assertTrue("B должен быть перед A", result.startsWith("B"));
        assertTrue("A должен быть после B", result.contains("-> A"));
    }
    
    /**
     * Проверяет простое ромбовидное наследование (A <- B, A <- C, B,C <- D)
     */
    @Test
    public void testDiamondInheritance() {
        NodeD nodeD = MixinFactory.createInstance(NodeD.class);
        
        // Порядок должен быть D -> B/C -> A
        String result = nodeD.getTopologicalOrder();
        System.out.println("Diamond inheritance order: " + result);
        
        assertTrue("D должен быть первым", result.startsWith("D"));
        
        // Проверяем, что A встречается только один раз (нет дублирования)
        int countA = result.split("A\\(").length - 1;
        assertEquals("A должен встречаться только один раз", 1, countA);
        
        // Проверяем общую структуру
        assertTrue("Порядок должен быть D -> (B или C) -> (C или B) -> A", 
                result.matches("D\\(\\d+\\) -> [BC]\\(\\d+\\) -> [BC]\\(\\d+\\) -> A\\(\\d+\\)"));
    }
    
    /**
     * Проверяет сложный граф наследования с несколькими путями
     */
    @Test
    public void testComplexGraph() {
        NodeF nodeF = MixinFactory.createInstance(NodeF.class);
        
        // Порядок должен быть F -> C/E -> B -> A
        String result = nodeF.getTopologicalOrder();
        System.out.println("Complex graph inheritance order: " + result);
        
        assertTrue("F должен быть первым", result.startsWith("F"));
        
        // Проверяем, что A и B встречаются только один раз (нет дублирования)
        int countA = result.split("A\\(").length - 1;
        int countB = result.split("B\\(").length - 1;
        
        assertEquals("A должен встречаться только один раз", 1, countA);
        assertEquals("B должен встречаться только один раз", 1, countB);
        
        // Проверяем общую структуру - один из возможных путей
        assertTrue("Структура наследования некорректна", 
                result.contains("F(") && result.contains("E(") && 
                result.contains("C(") && result.contains("B(") && 
                result.contains("A("));
    }
    
    /**
     * Проверяет различные точки входа в граф, которые должны давать
     * топологически эквивалентный результат
     */
    @Test
    public void testDifferentEntryPoints() {
        // Сбрасываем счетчики
        NodeA.resetVisitOrder();
        NodeB.resetVisitOrder();
        NodeC.resetVisitOrder();
        
        // Создаем экземпляры с разных точек входа
        NodeB nodeB = MixinFactory.createInstance(NodeB.class);
        String resultB = nodeB.getTopologicalOrder();
        
        // Очищаем кэш и сбрасываем счетчики
        MixinFactory.clearCache();
        NodeA.resetVisitOrder();
        NodeB.resetVisitOrder();
        NodeC.resetVisitOrder();
        
        NodeC nodeC = MixinFactory.createInstance(NodeC.class);
        String resultC = nodeC.getTopologicalOrder();
        
        System.out.println("Entry B order: " + resultB);
        System.out.println("Entry C order: " + resultC);
        
        // Проверяем, что оба пути заканчиваются на A и имеют корректную структуру
        assertTrue("Путь B должен заканчиваться на A", resultB.endsWith("A(1)"));
        assertTrue("Путь C должен заканчиваться на A", resultC.endsWith("A(1)"));
        
        assertTrue("Путь B имеет некорректную структуру", resultB.matches("B\\(\\d+\\) -> A\\(\\d+\\)"));
        assertTrue("Путь C имеет некорректную структуру", resultC.matches("C\\(\\d+\\) -> A\\(\\d+\\)"));
    }
} 