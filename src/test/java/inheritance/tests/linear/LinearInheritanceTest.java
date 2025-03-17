package inheritance.tests.linear;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Тест для проверки линейного наследования (A -> B -> C)
 */
public class LinearInheritanceTest {
    
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
    public void testLinearInheritance() {
        // Создаем экземпляр класса C с использованием фабрики миксинов
        ClassC instanceC = MixinFactory.createInstance(ClassC.class);
        
        // Проверяем результат вызова метода testMethod
        // Ожидаем "CBA" - результат вызова методов по цепочке C -> B -> A
        String result = instanceC.testMethod();
        assertEquals("CBA", result);
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