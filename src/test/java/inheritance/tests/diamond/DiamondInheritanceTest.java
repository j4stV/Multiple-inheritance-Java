package inheritance.tests.diamond;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Тест для проверки ромбовидного наследования (A -> B,C -> D)
 * Проверяет правильность построения цепочки наследования
 * и доступность методов из разных веток наследования
 */
public class DiamondInheritanceTest {
    
    @Before
    public void setUp() {
        // Отключаем вывод отладочной информации
        MixinFactory.setDebugEnabled(false);
        // Очищаем кэш инстансов перед тестом
        MixinFactory.clearCache();
    }
    
    @After
    public void tearDown() {
        // Удаляем сгенерированные файлы после тестов
        cleanupGeneratedFiles();
    }
    
    @Test
    public void testDiamondInheritance() {
        // Создаем экземпляр класса D с использованием фабрики миксинов
        ClassD instanceD = MixinFactory.createInstance(ClassD.class);
        
        // Проверяем результат вызова метода testMethod
        // Ожидаем "DBCA" в результате топологической сортировки
        String result = instanceD.testMethod();
        assertEquals("DBCA", result);
        
        // Проверяем доступность специфических методов B
        String specificResult = instanceD.callParentSpecificMethods();
        assertEquals("B-specific", specificResult);
        
        // Проверяем родительские связи через instanceof
        assertTrue("Родитель D должен быть экземпляром B", instanceD.parent instanceof ClassB);
    }
    
    /**
     * Удаление сгенерированных файлов
     */
    private void cleanupGeneratedFiles() {
        // Удаляем сгенерированные файлы с расширением .class
        File generatedDir = new File("generated");
        if (generatedDir.exists() && generatedDir.isDirectory()) {
            File[] files = generatedDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        file.delete();
                    }
                }
            }
        }
    }
} 