package inheritance.processor;

import inheritance.annotations.Root;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("inheritance.annotations.Root")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class RootProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Root.class)) {
            if (element.getKind() != ElementKind.INTERFACE) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Только интерфейсы могут быть помечены аннотацией @Root", element);
                continue;
            }

            TypeElement interfaceElement = (TypeElement) element;
            String packageName = elementUtils.getPackageOf(interfaceElement).getQualifiedName().toString();
            String interfaceName = interfaceElement.getSimpleName().toString();
            String rootClassName = interfaceName + "Root";

            try {
                generateRootClass(packageName, interfaceName, rootClassName, interfaceElement);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Ошибка при генерации класса: " + e.getMessage(), element);
            }
        }
        return true;
    }

    private void generateRootClass(String packageName, String interfaceName, String rootClassName, TypeElement interfaceElement) throws IOException {
        JavaFileObject javaFile = filer.createSourceFile(packageName + "." + rootClassName, interfaceElement);
        List<? extends Element> enclosedElements = interfaceElement.getEnclosedElements()
            .stream()
            .filter(e -> e.getKind() == ElementKind.METHOD)
            .collect(Collectors.toList());
        
        // Получаем параметры типа интерфейса
        List<? extends TypeParameterElement> typeParameters = interfaceElement.getTypeParameters();
        
        try (PrintWriter out = new PrintWriter(javaFile.openWriter())) {
            // Создаем заголовок файла
            out.println("package " + packageName + ";");
            out.println();
            out.println("import inheritance.annotations.Mixin;");
            out.println("import inheritance.factory.MixinFactory;");
            out.println("import java.lang.reflect.Method;");
            out.println("import java.lang.reflect.InvocationTargetException;");
            out.println("import java.lang.reflect.Type;");
            out.println("import java.lang.reflect.ParameterizedType;");
            out.println();
            out.println("/**");
            out.println(" * Автоматически сгенерированный корневой класс для интерфейса " + interfaceName);
            if (!typeParameters.isEmpty()) {
                out.println(" * @param <" + formatTypeParameters(typeParameters) + "> Параметры типа");
            }
            out.println(" */");
            
            // Создаем объявление класса с параметрами типа, если они есть
            out.print("public abstract class " + rootClassName);
            if (!typeParameters.isEmpty()) {
                out.print("<" + formatTypeParameters(typeParameters) + ">");
            }
            out.print(" implements " + interfaceName);
            if (!typeParameters.isEmpty()) {
                out.print("<" + formatTypeParameters(typeParameters) + ">");
            }
            out.println(" {");
            out.println();

            // Поле для хранения родительского объекта
            out.println("    protected Object parent;");
            out.println();
            
            // Добавляем статическое поле для отслеживания глубины рекурсии
            out.println("    private static final int MAX_CALL_DEPTH = 20;");
            out.println("    private static final ThreadLocal<Integer> callDepth = new ThreadLocal<Integer>() {");
            out.println("        @Override");
            out.println("        protected Integer initialValue() {");
            out.println("            return 0;");
            out.println("        }");
            out.println("    };");
            out.println();

            // Пустой конструктор без логики инициализации
            out.println("    public " + rootClassName + "() {");
            out.println("        // Инициализация выполняется фабрикой MixinFactory");
            out.println("    }");
            out.println();

            // Статический метод для создания экземпляров через фабрику
            out.println("    /**");
            out.println("     * Создает экземпляр класса через фабрику MixinFactory");
            out.println("     * @param clazz Класс для создания экземпляра");
            out.println("     * @param <T> Тип создаваемого экземпляра");
            out.println("     * @return Настроенный экземпляр класса");
            out.println("     */");
            out.println("    public static <T> T createInstance(Class<T> clazz) {");
            out.println("        return MixinFactory.createInstance(clazz);");
            out.println("    }");
            out.println();

            // Для каждого метода в интерфейсе создаем метод nextXXX
            for (Element methodElement : enclosedElements) {
                ExecutableElement method = (ExecutableElement) methodElement;
                String methodName = method.getSimpleName().toString();
                String nextMethodName = "next" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);

                // Получаем типы параметров и возвращаемый тип
                String returnType = method.getReturnType().toString();
                
                List<? extends VariableElement> parameters = method.getParameters();
                String parameterList = parameters.stream()
                    .map(p -> p.asType() + " " + p.getSimpleName())
                    .collect(Collectors.joining(", "));

                String parameterNames = parameters.stream()
                    .map(p -> p.getSimpleName().toString())
                    .collect(Collectors.joining(", "));

                // Генерируем метод nextXXX с проверкой глубины вызовов
                out.println("    protected " + returnType + " " + nextMethodName + "(" + parameterList + ") {");
                out.println("        if (parent == null) {");
                if (!returnType.equals("void")) {
                    out.println("            return " + getDefaultReturnValue(returnType) + ";");
                } else {
                    out.println("            return;");
                }
                out.println("        }");
                out.println();
                
                // Проверка и увеличение счетчика глубины вызовов
                out.println("        int currentDepth = callDepth.get();");
                out.println("        if (currentDepth >= MAX_CALL_DEPTH) {");
                out.println("            System.out.println(\"Превышена максимальная глубина вызовов методов: \" + MAX_CALL_DEPTH + \". Возможно, обнаружен цикл.\");");
                if (!returnType.equals("void")) {
                    out.println("            return " + getDefaultReturnValue(returnType) + ";");
                } else {
                    out.println("            return;");
                }
                out.println("        }");
                out.println("        callDepth.set(currentDepth + 1);");
                out.println();
                
                out.println("        try {");
                
                // Для дженериков используем другой подход к получению метода
                if (parameters.isEmpty()) {
                    out.println("            Method method = parent.getClass().getMethod(\"" + methodName + "\");");
                } else {
                    out.println("            // Создаем массив типов параметров");
                    out.println("            Class<?>[] paramTypes = new Class<?>[" + parameters.size() + "];");
                    
                    // Для каждого параметра определяем его тип
                    for (int i = 0; i < parameters.size(); i++) {
                        VariableElement param = parameters.get(i);
                        String paramType = param.asType().toString();
                        
                        // Обработка типовых параметров
                        if (isTypeVariable(paramType, typeParameters)) {
                            out.println("            // Для дженериков используем Object.class");
                            out.println("            paramTypes[" + i + "] = Object.class;");
                        } else {
                            // Для примитивных типов и обычных классов
                            out.println("            paramTypes[" + i + "] = " + getClassForType(paramType) + ";");
                        }
                    }
                    
                    out.println("            Method method = parent.getClass().getMethod(\"" + methodName + "\", paramTypes);");
                }

                if (!returnType.equals("void")) {
                    out.println("            " + returnType + " result = (" + returnType + ") method.invoke(parent" + (parameterNames.isEmpty() ? "" : ", " + parameterNames) + ");");
                    out.println("            callDepth.set(currentDepth); // Восстанавливаем счетчик");
                    out.println("            return result;");
                } else {
                    out.println("            method.invoke(parent" + (parameterNames.isEmpty() ? "" : ", " + parameterNames) + ");");
                    out.println("            callDepth.set(currentDepth); // Восстанавливаем счетчик");
                }

                out.println("        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {");
                out.println("            callDepth.set(currentDepth); // Восстанавливаем счетчик в случае ошибки");
                out.println("            throw new RuntimeException(\"Ошибка при вызове метода родительского класса\", e);");
                out.println("        }");
                out.println("    }");
                out.println();
            }

            out.println("}");
        }
    }
    
    /**
     * Проверяет, является ли тип параметром типа из списка параметров
     * 
     * @param typeName Имя типа для проверки
     * @param typeParameters Список параметров типа
     * @return true, если тип является параметром типа
     */
    private boolean isTypeVariable(String typeName, List<? extends TypeParameterElement> typeParameters) {
        for (TypeParameterElement tp : typeParameters) {
            if (tp.getSimpleName().toString().equals(typeName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Возвращает строку для получения объекта Class для заданного типа
     * 
     * @param typeName Имя типа
     * @return Строка для получения Class
     */
    private String getClassForType(String typeName) {
        switch (typeName) {
            case "byte": return "byte.class";
            case "short": return "short.class";
            case "int": return "int.class";
            case "long": return "long.class";
            case "float": return "float.class";
            case "double": return "double.class";
            case "boolean": return "boolean.class";
            case "char": return "char.class";
            case "void": return "void.class";
            default:
                // Обрабатываем параметризованные типы
                if (typeName.contains("<")) {
                    // Для дженериков берем только базовый тип
                    String baseType = typeName.substring(0, typeName.indexOf('<'));
                    return baseType + ".class";
                }
                // Для обычных классов
                return typeName + ".class";
        }
    }
    
    /**
     * Форматирует параметры типа для использования в объявлении класса
     * 
     * @param typeParameters Список параметров типа
     * @return Строка с форматированными параметрами типа
     */
    private String formatTypeParameters(List<? extends TypeParameterElement> typeParameters) {
        return typeParameters.stream()
            .map(tp -> {
                String typeName = tp.getSimpleName().toString();
                List<? extends TypeMirror> bounds = tp.getBounds();
                
                if (bounds.isEmpty() || bounds.size() == 1 && bounds.get(0).toString().equals("java.lang.Object")) {
                    return typeName;
                } else {
                    String boundsList = bounds.stream()
                        .map(TypeMirror::toString)
                        .collect(Collectors.joining(" & "));
                    return typeName + " extends " + boundsList;
                }
            })
            .collect(Collectors.joining(", "));
    }

    private String getDefaultReturnValue(String returnType) {
        switch (returnType) {
            case "byte":
            case "short":
            case "int":
            case "long":
            case "char":
                return "0";
            case "float":
            case "double":
                return "0.0";
            case "boolean":
                return "false";
            default:
                return "null";
        }
    }
} 