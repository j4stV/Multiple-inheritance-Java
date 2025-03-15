import example.diamond.*;

/**
 * Пример демонстрации сложного множественного наследования
 * с классом F, наследующимся от D и A одновременно
 */
public class ComplexInheritanceExample {
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрация сложного множественного наследования ===");
        
        try {
            // Создаем экземпляр класса F
            System.out.println("Создание экземпляра класса F...");
            F f = SomeInterfaceRoot.createInstance(F.class);
            System.out.println("Экземпляр класса F создан успешно.");
            
            // Проверяем интерфейсы и типы
            System.out.println("\nПроверка интерфейсов и типов:");
            System.out.println("f instanceof SomeInterface: " + (f instanceof SomeInterface));
            System.out.println("f instanceof F: " + (f instanceof F));
            // Примечание: f не будет экземпляром D, так как в Java нет прямого наследования типов
            
            // Вызываем методы
            System.out.println("\nВызов методов:");
            System.out.println("Вызов метода f.method():");
            f.method();
            
            System.out.println("\nВызов уникального метода f.methodF():");
            f.methodF();
            
            // Отображаем структуру наследования

            
            // Исследуем структуру наследования через рефлексию
            
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Демонстрация завершена ===");
    }
    
    /**
     * Исследует структуру наследования объекта через рефлексию
     * @param f объект для исследования
     */
    private static void examineInheritanceStructure(F f) {
        try {
            // Получаем первого родителя - должен быть D
            java.lang.reflect.Field parentField = f.getClass().getSuperclass().getDeclaredField("parent");
            parentField.setAccessible(true);
            Object firstParent = parentField.get(f);
            
            System.out.println("Первый родитель F: " + 
                    (firstParent != null ? firstParent.getClass().getName() : "null"));
            
            if (firstParent != null) {
                // Получаем второго родителя - должен быть A (или null, если его нет)
                java.lang.reflect.Field nextParentField = firstParent.getClass().getSuperclass().getDeclaredField("parent");
                nextParentField.setAccessible(true);
                Object secondParent = nextParentField.get(firstParent);
                
                System.out.println("Второй родитель: " + 
                        (secondParent != null ? secondParent.getClass().getName() : "null"));
                
                // Если F наследуется от D, которое имеет сложную цепочку наследования,
                // то мы увидим более глубокую структуру наследования
                if (secondParent != null) {
                    java.lang.reflect.Field thirdParentField = secondParent.getClass().getSuperclass().getDeclaredField("parent");
                    thirdParentField.setAccessible(true);
                    Object thirdParent = thirdParentField.get(secondParent);
                    
                    System.out.println("Третий родитель: " + 
                            (thirdParent != null ? thirdParent.getClass().getName() : "null"));
                    
                    if (thirdParent != null) {
                        java.lang.reflect.Field fourthParentField = thirdParent.getClass().getSuperclass().getDeclaredField("parent");
                        fourthParentField.setAccessible(true);
                        Object fourthParent = fourthParentField.get(thirdParent);
                        
                        System.out.println("Четвертый родитель: " + 
                                (fourthParent != null ? fourthParent.getClass().getName() : "null"));
                        
                        if (fourthParent != null) {
                            java.lang.reflect.Field fifthParentField = fourthParent.getClass().getSuperclass().getDeclaredField("parent");
                            fifthParentField.setAccessible(true);
                            Object fifthParent = fifthParentField.get(fourthParent);
                            
                            System.out.println("Пятый родитель: " + 
                                    (fifthParent != null ? fifthParent.getClass().getName() : "null"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при исследовании структуры наследования: " + e.getMessage());
        }
    }
} 