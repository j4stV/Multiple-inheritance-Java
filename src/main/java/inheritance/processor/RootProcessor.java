package inheritance.processor;

import inheritance.annotations.Root;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
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

        try (PrintWriter out = new PrintWriter(javaFile.openWriter())) {
            // Создаем заголовок файла
            out.println("package " + packageName + ";");
            out.println();
            out.println("import inheritance.annotations.Mixin;");
            out.println("import java.lang.reflect.Method;");
            out.println("import java.lang.reflect.InvocationTargetException;");
            out.println();
            out.println("/**");
            out.println(" * Автоматически сгенерированный корневой класс для интерфейса " + interfaceName);
            out.println(" */");
            out.println("public abstract class " + rootClassName + " implements " + interfaceName + " {");
            out.println();

            // Поле для хранения родительского объекта
            out.println("    protected Object parent;");
            out.println("    protected Object[] mixinParents;");
            out.println();

            // Конструктор
            out.println("    public " + rootClassName + "() {");
            out.println("        Mixin mixin = this.getClass().getAnnotation(Mixin.class);");
            out.println("        if (mixin != null && mixin.value().length > 0) {");
            out.println("            try {");
            out.println("                // Установка первого родителя");
            out.println("                parent = mixin.value()[0].getDeclaredConstructor().newInstance();");
            out.println("                ");
            out.println("                // Создание массива для всех родителей");
            out.println("                mixinParents = new Object[mixin.value().length];");
            out.println("                mixinParents[0] = parent;");
            out.println("                ");
            out.println("                // Создание дополнительных родителей");
            out.println("                for (int i = 1; i < mixin.value().length; i++) {");
            out.println("                    mixinParents[i] = mixin.value()[i].getDeclaredConstructor().newInstance();");
            out.println("                }");
            out.println("                ");
            out.println("                // Установка цепочки делегации для классов");
            out.println("                for (int i = 0; i < mixinParents.length - 1; i++) {");
            out.println("                    Object current = mixinParents[i];");
            out.println("                    Object next = mixinParents[i + 1];");
            out.println("                    ");
            out.println("                    // Если родитель является инстансом корневого класса, то устанавливаем ему следующего родителя");
            out.println("                    if (current.getClass().getSuperclass().getSimpleName().endsWith(\"Root\") && current.getClass().getSuperclass().getDeclaredField(\"parent\") != null){");
            out.println("                        try {");
            out.println("                            current.getClass().getSuperclass().getDeclaredField(\"parent\").set(current, next);");
            out.println("                        } catch (NoSuchFieldException e) {");
            out.println("                            throw new RuntimeException(\"Не удалось установить цепочку делегации: поле 'parent' не найдено\", e);");
            out.println("                        }");
            out.println("                    }");
            out.println("                }");
            out.println("            } catch (Exception e) {");
            out.println("                throw new RuntimeException(\"Не удалось создать экземпляры родительских классов\", e);");
            out.println("            }");
            out.println("        }");
            out.println("    }");
            out.println();

            // Для каждого метода в интерфейсе создаем метод nextXXX
            for (Element methodElement : enclosedElements) {
                ExecutableElement method = (ExecutableElement) methodElement;
                String methodName = method.getSimpleName().toString();
                String nextMethodName = "next" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);

                // Получаем типы параметров и возвращаемый тип
                String returnType = method.getReturnType().toString();
                if (returnType.equals("void")) {
                    returnType = "void";
                }

                List<? extends VariableElement> parameters = method.getParameters();
                String parameterList = parameters.stream()
                    .map(p -> p.asType() + " " + p.getSimpleName())
                    .collect(Collectors.joining(", "));

                String parameterTypesList = parameters.stream()
                    .map(p -> p.asType().toString() + ".class")
                    .collect(Collectors.joining(", "));

                String parameterNames = parameters.stream()
                    .map(p -> p.getSimpleName().toString())
                    .collect(Collectors.joining(", "));

                // Генерируем метод nextXXX
                out.println("    protected " + returnType + " " + nextMethodName + "(" + parameterList + ") {");
                out.println("        if (parent == null) {");
                if (!returnType.equals("void")) {
                    out.println("            return " + getDefaultReturnValue(returnType) + ";");
                } else {
                    out.println("            return;");
                }
                out.println("        }");
                out.println();
                out.println("        try {");

                if (parameters.isEmpty()) {
                    out.println("            Method method = parent.getClass().getMethod(\"" + methodName + "\");");
                } else {
                    out.println("            Method method = parent.getClass().getMethod(\"" + methodName + "\", " + parameterTypesList + ");");
                }

                if (!returnType.equals("void")) {
                    out.println("            return (" + returnType + ") method.invoke(parent" + (parameterNames.isEmpty() ? "" : ", " + parameterNames) + ");");
                } else {
                    out.println("            method.invoke(parent" + (parameterNames.isEmpty() ? "" : ", " + parameterNames) + ");");
                }

                out.println("        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {");
                out.println("            throw new RuntimeException(\"Ошибка при вызове метода родительского класса\", e);");
                out.println("        }");

                out.println("    }");
                out.println();
            }

            // Добавляем методы доступа к миксинам
            out.println("    /**");
            out.println("     * Возвращает родительский объект по индексу");
            out.println("     * @param index индекс родительского объекта");
            out.println("     * @return родительский объект или null, если индекс выходит за пределы");
            out.println("     */");
            out.println("    protected Object getMixinParent(int index) {");
            out.println("        if (mixinParents == null || index < 0 || index >= mixinParents.length) {");
            out.println("            return null;");
            out.println("        }");
            out.println("        return mixinParents[index];");
            out.println("    }");
            out.println();

            out.println("    /**");
            out.println("     * Возвращает количество родительских объектов");
            out.println("     * @return количество родительских объектов");
            out.println("     */");
            out.println("    protected int getMixinParentsCount() {");
            out.println("        return mixinParents == null ? 0 : mixinParents.length;");
            out.println("    }");
            out.println();

            out.println("}");
        }
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