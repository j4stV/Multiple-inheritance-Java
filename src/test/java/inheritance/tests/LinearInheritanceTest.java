package inheritance.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import inheritance.annotations.Root;
import inheritance.annotations.Mixin;
import inheritance.factory.MixinFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Тест для проверки линейной цепочки наследования (A → B → C)
 */
public class LinearInheritanceTest {
    
    private static final String GENERATED_DIR = "build/generated/sources/annotationProcessor/java/main";
    private List<String> methodCallOrder;

    @Root
    public interface TestLinearInterface {
        void testMethod();
    }

    public class A extends TestLinearInterfaceRoot {
        @Override
        public void testMethod() {
            methodCallOrder.add("A");
            nextTestMethod();
        }
    }

    @Mixin(A.class)
    public class B extends TestLinearInterfaceRoot {
        @Override
        public void testMethod() {
            methodCallOrder.add("B");
            nextTestMethod();
        }
    }

    @Mixin(B.class)
    public class C extends TestLinearInterfaceRoot {
        @Override
        public void testMethod() {
            methodCallOrder.add("C");
            nextTestMethod();
        }
    }

    @Before
    public void setUp() {
        methodCallOrder = new ArrayList<>();
        MixinFactory.setDebugEnabled(false); // Отключаем отладочный вывод
    }

    @After
    public void tearDown() {
        // Очищаем сгенерированные файлы
        deleteGeneratedFiles();
    }

    @Test
    public void testLinearInheritance() {
        // Создаем экземпляр класса C с линейным наследованием C → B → A
        C c = MixinFactory.createInstance(C.class);
        
        // Вызываем метод, который должен пройти по цепочке
        c.testMethod();
        
        // Проверяем порядок вызовов
        assertEquals(3, methodCallOrder.size());
        assertEquals("C", methodCallOrder.get(0));
        assertEquals("B", methodCallOrder.get(1));
        assertEquals("A", methodCallOrder.get(2));
    }

    /**
     * Удаляет сгенерированные файлы после выполнения теста
     */
    private void deleteGeneratedFiles() {
        File generatedDir = new File(GENERATED_DIR);
        if (generatedDir.exists()) {
            deleteDirectory(generatedDir);
        }
    }

    /**
     * Рекурсивно удаляет директорию и все её содержимое
     */
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
} 