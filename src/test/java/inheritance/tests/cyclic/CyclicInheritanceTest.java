package inheritance.tests.cyclic;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Тест для проверки циклического наследования (A -> B -> C -> A)
 * Проверяет правильность обработки циклических зависимостей
 * и корректное обнаружение циклов в топологической сортировке
 */
public class CyclicInheritanceTest {
    
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
    public void testCyclicInheritance() {
        try {
            // Попытка создать экземпляр класса A с циклической зависимостью
            ClassA instanceA = MixinFactory.createInstance(ClassA.class);
            fail("Должна быть выброшена ошибка о циклической зависимости");
        } catch (RuntimeException e) {
            // Проверяем, что выброшено исключение с правильным сообщением
            assertTrue("Сообщение об ошибке должно содержать информацию о циклической зависимости", 
                    e.getMessage().contains("цикл") || e.getMessage().contains("cyclic"));
        }
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