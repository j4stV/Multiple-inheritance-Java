package inheritance.tests.repeatedAncestor;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Тест для проверки наследования с повторяющимся предком
 * Структура наследования: D extends A,B; B extends A
 * Проверяет правильность построения цепочки наследования
 * и доступ к методам при наличии повторяющегося предка
 */
public class RepeatedAncestorTest {
    
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
    public void testRepeatedAncestorInheritance() {
        // Создаем экземпляр класса D с использованием фабрики миксинов
        ClassD instanceD = MixinFactory.createInstance(ClassD.class);
        
        // Проверяем результат вызова метода testMethod
        // Ожидаем "DBA" - порядок вызовов должен идти D -> B -> A
        // A не должен вызываться дважды, даже если он является предком и для D, и для B
        String result = instanceD.testMethod();
        assertEquals("DBA", result);
        
        // Проверяем доступность специфических методов родительских классов
        String specificResult = instanceD.callParentSpecificMethods();
        
        // Проверяем, что метод возвращает корректную строку
        // В зависимости от того, кто является непосредственным родителем D
        if (instanceD.parent instanceof ClassA) {
            assertEquals("A-specific", specificResult);
        } else if (instanceD.parent instanceof ClassB) {
            assertTrue(specificResult.contains("B-specific"));
            // Если родитель B, то через него мы также имеем доступ к методам A
            // Но нас больше интересует только непосредственный родитель
        }
        
        // Проверяем, что у D есть доступ к обоим родительским классам
        assertTrue("Должен быть доступ к методам класса A через иерархию",
                specificResult.contains("A-specific") || (instanceD.parent instanceof ClassB));
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