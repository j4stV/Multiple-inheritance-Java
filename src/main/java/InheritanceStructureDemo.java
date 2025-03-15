import example.diamond.*;
import inheritance.factory.MixinFactory;

/**
 * Демонстрация структуры наследования в проекте
 */
public class InheritanceStructureDemo {
    
    public static void main(String[] args) {
        boolean debug = false;
        
        for (String arg : args) {
            if (arg.equals("--debug=true")) {
                debug = true;
                MixinFactory.setDebugEnabled(true);
            }
        }
        
        System.out.println("=== Демонстрация структуры наследования ===");
        
        // Создаем экземпляр класса F
        F f = MixinFactory.createInstance(F.class);
        
        // Выводим структуру наследования
        System.out.println("\n=== Структура наследования класса F ===");
        printInheritanceStructure(f);
        
        System.out.println("\n=== Демонстрация завершена ===");
    }
    
    /**
     * Выводит структуру наследования для объекта
     */
    private static void printInheritanceStructure(Object obj) {
        if (obj == null) {
            System.out.println("Объект равен null");
            return;
        }
        
        Class<?> clazz = obj.getClass();
        System.out.println("Класс: " + clazz.getSimpleName());
        
        try {
            // Получаем поле parent
            java.lang.reflect.Field parentField = null;
            Class<?> currentClass = clazz;
            
            while (currentClass != null && parentField == null) {
                try {
                    parentField = currentClass.getDeclaredField("parent");
                } catch (NoSuchFieldException e) {
                    currentClass = currentClass.getSuperclass();
                }
            }
            
            if (parentField != null) {
                parentField.setAccessible(true);
                Object parent = parentField.get(obj);
                
                if (parent != null) {
                    System.out.println("└── Родитель: " + parent.getClass().getSimpleName());
                    printParentChain(parent, "    ");
                } else {
                    System.out.println("└── Нет родителя");
                }
            } else {
                System.out.println("└── Поле parent не найдено");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при получении структуры наследования: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Рекурсивно выводит цепочку родителей
     */
    private static void printParentChain(Object obj, String indent) {
        if (obj == null) return;
        
        try {
            // Получаем поле parent
            java.lang.reflect.Field parentField = null;
            Class<?> currentClass = obj.getClass();
            
            while (currentClass != null && parentField == null) {
                try {
                    parentField = currentClass.getDeclaredField("parent");
                } catch (NoSuchFieldException e) {
                    currentClass = currentClass.getSuperclass();
                }
            }
            
            if (parentField != null) {
                parentField.setAccessible(true);
                Object parent = parentField.get(obj);
                
                if (parent != null) {
                    System.out.println(indent + "└── " + parent.getClass().getSimpleName());
                    printParentChain(parent, indent + "    ");
                }
            }
        } catch (Exception e) {
            System.out.println(indent + "Ошибка: " + e.getMessage());
        }
    }
} 