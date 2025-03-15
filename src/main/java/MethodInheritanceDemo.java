import example.diamond.*;
import inheritance.annotations.Mixin;
import inheritance.factory.MixinFactory;

/**
 * Класс для демонстрации вызова методов родительских классов
 * через цепочку наследования.
 */
public class MethodInheritanceDemo {
    
    public static void main(String[] args) {
        boolean debug = false;
        
        for (String arg : args) {
            if (arg.equals("--debug=true")) {
                debug = true;
                MixinFactory.setDebugEnabled(true);
            }
        }
        
        System.out.println("=== Демонстрация наследования методов ===");
        
        // Создаем экземпляр класса F
        F f = MixinFactory.createInstance(F.class);
        
        System.out.println("\n=== Тест доступности методов всех родительских классов ===");
        
        // Вызываем метод из класса F
        System.out.println("\nМетод F.methodF():");
        f.methodF();
        
        // Вызываем метод из класса D (доступен через parent)
        System.out.println("\nМетод D.methodD():");
        if (f.getParent() instanceof D) {
            ((D)f.getParent()).methodD();
        } else {
            System.out.println("ERROR: Родитель F не является экземпляром класса D");
        }
        
        // Вызываем метод класса B (доступен через f -> D -> B)
        testMethodAccess(f, "B.methodB()", () -> {
            if (f.getParent() instanceof D) {
                D d = (D) f.getParent();
                if (d.getParent() instanceof B) {
                    ((B)d.getParent()).methodB();
                } else {
                    System.out.println("ERROR: Родитель D не является экземпляром класса B");
                }
            } else {
                System.out.println("ERROR: D не доступен от F");
            }
        });
        
        // Вызываем метод класса C (доступен через f -> D -> C)
        testMethodAccess(f, "C.methodC()", () -> {
            if (f.getParent() instanceof D) {
                D d = (D) f.getParent();
                if (d.getParent() instanceof C) {
                    ((C) d.getParent()).methodC();
                } else {
                    System.out.println("ERROR: C не является родителем D");
                }
            } else {
                System.out.println("ERROR: D не доступен от F");
            }
        });
        
        // Вызываем метод класса E (доступен через f -> D -> E)
        testMethodAccess(f, "E.methodE()", () -> {
            if (f.getParent() instanceof D) {
                D d = (D) f.getParent();
                if (d.getParent() instanceof E) {
                    ((E) d.getParent()).methodE();
                } else {
                    System.out.println("ERROR: E не является родителем D");
                }
            } else {
                System.out.println("ERROR: D не доступен от F");
            }
        });
        
        // Вызываем метод класса A (общего предка для всех)
        testMethodAccess(f, "A.method()", () -> {
            f.method();
        });
        
        System.out.println("\n=== Проверка иерархии классов ===");
        
        printParentChain(f);
        
        System.out.println("\n=== Демонстрация завершена ===");
    }
    
    /**
     * Вспомогательный метод для безопасного вызова методов
     */
    private static void testMethodAccess(Object obj, String methodName, Runnable methodCall) {
        System.out.println("\nМетод " + methodName + ":");
        try {
            methodCall.run();
        } catch (Exception e) {
            System.out.println("ERROR: Не удалось вызвать метод: " + e.getMessage());
        }
    }
    
    /**
     * Выводит цепочку родителей для объекта
     */
    private static void printParentChain(Object obj) {
        if (obj == null) {
            return;
        }
        
        Class<?> clazz = obj.getClass();
        System.out.println("Объект класса: " + clazz.getSimpleName());
        
        try {
            java.lang.reflect.Field parentField = getParentField(clazz);
            if (parentField != null) {
                parentField.setAccessible(true);
                Object parent = parentField.get(obj);
                
                System.out.println("  └── Родитель: " + (parent != null ? parent.getClass().getSimpleName() : "null"));
                
                if (parent != null) {
                    printParentChainRecursive(parent, "      ");
                }
            } else {
                System.out.println("  └── (нет поля parent)");
            }
        } catch (Exception e) {
            System.out.println("  └── Ошибка при получении родителя: " + e.getMessage());
        }
    }
    
    /**
     * Рекурсивно выводит цепочку родителей
     */
    private static void printParentChainRecursive(Object obj, String indent) {
        if (obj == null) {
            return;
        }
        
        try {
            Class<?> clazz = obj.getClass();
            java.lang.reflect.Field parentField = getParentField(clazz);
            
            if (parentField != null) {
                parentField.setAccessible(true);
                Object parent = parentField.get(obj);
                
                if (parent != null) {
                    System.out.println(indent + "└── Родитель: " + parent.getClass().getSimpleName());
                    printParentChainRecursive(parent, indent + "    ");
                } else {
                    System.out.println(indent + "└── (нет родительского объекта)");
                }
            } else {
                System.out.println(indent + "└── (нет поля parent)");
            }
        } catch (Exception e) {
            System.out.println(indent + "└── Ошибка: " + e.getMessage());
        }
    }
    
    /**
     * Находит поле parent в классе или его предках
     */
    private static java.lang.reflect.Field getParentField(Class<?> clazz) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField("parent");
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
} 