package inheritance.tests.generic;

import inheritance.factory.MixinFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Тесты для проверки поддержки дженериков в системе множественного наследования
 */
public class GenericInheritanceTest {
    
    @Before
    public void setUp() {
        // Отключаем вывод отладочной информации
        MixinFactory.setDebugEnabled(false);
        // Очищаем кэш инстансов перед тестом
        MixinFactory.clearCache();
    }
    
    @After
    public void tearDown() {
        // Очищаем кэш после теста
        MixinFactory.clearCache();
    }
    
    /**
     * Тест для проверки базовой работы с дженериками в классе StringContainer
     */
    @Test
    public void testStringContainer() {
        // Создаем экземпляр базового класса
        StringContainer stringContainer = MixinFactory.createInstance(StringContainer.class);
        
        // Устанавливаем и получаем значение
        stringContainer.setValue("Тест дженериков");
        assertEquals("Тест дженериков", stringContainer.getValue());
        
        // Проверяем трансформацию
        assertEquals("Префикс: Тест дженериков", stringContainer.transformValue("Префикс"));
    }
    
    /**
     * Тест для проверки наследования с дженериками
     */
    @Test
    public void testEnhancedStringContainer() {
        // Создаем экземпляр производного класса
        EnhancedStringContainer enhancedContainer = MixinFactory.createInstance(EnhancedStringContainer.class);
        
        // Устанавливаем и получаем значение
        enhancedContainer.setValue("Тест дженериков");
        assertEquals("Тест дженериков", enhancedContainer.getValue());
        
        // Проверяем трансформацию с преобразованием к верхнему регистру
        assertEquals("ПРЕФИКС: ТЕСТ ДЖЕНЕРИКОВ", enhancedContainer.transformValue("Префикс"));
        
        // Проверяем дополнительный метод
        assertEquals("тест дженериков", enhancedContainer.getLowerCaseValue());
    }
    
    /**
     * Тест для проверки работы с другим типом дженерика
     */
    @Test
    public void testIntegerContainer() {
        // Создаем экземпляр класса с целочисленным типом
        IntegerContainer intContainer = MixinFactory.createInstance(IntegerContainer.class);
        
        // Устанавливаем и получаем значение
        intContainer.setValue(42);
        assertEquals(Integer.valueOf(42), intContainer.getValue());
        
        // Проверяем трансформацию
        assertEquals("Число (число): 42", intContainer.transformValue("Число"));
        
        // Проверяем дополнительный метод
        assertEquals(Integer.valueOf(126), intContainer.multiply(3));
    }
    
    /**
     * Тест для проверки работы с множественным наследованием различных типов
     */
    @Test
    public void testMixedTypeContainer() {
        // Создаем экземпляр смешанного класса
        MixedTypeContainer mixedContainer = MixinFactory.createInstance(MixedTypeContainer.class);
        
        // Тест со строковым значением, которое не является числом
        mixedContainer.setValue("Тест");
        assertEquals("Тест", mixedContainer.getValue());
        assertEquals(null, mixedContainer.getNumericValue());
        
        // Проверяем трансформацию
        assertTrue(mixedContainer.transformValue("Префикс").startsWith("ПРЕФИКС"));
        assertFalse(mixedContainer.transformValue("Префикс").contains("Числовое значение"));
        
        // Тест с числовым значением в виде строки
        mixedContainer.setValue("123");
        assertEquals("123", mixedContainer.getValue());
        assertEquals(Integer.valueOf(123), mixedContainer.getNumericValue());
        
        // Проверяем трансформацию с числовым значением
        String transformed = mixedContainer.transformValue("Число");
        assertTrue(transformed.startsWith("ЧИСЛО"));
        assertTrue(transformed.contains("[Числовое значение: 123]"));
        
        // Проверяем комбинированный метод
        String combined = mixedContainer.getCombinedInfo(2);
        assertTrue(combined.contains("Текст: 123"));
        assertTrue(combined.contains("В нижнем регистре: 123"));
        assertTrue(combined.contains("Числовое значение: 123"));
        assertTrue(combined.contains("После умножения на 2: 246"));
    }
}