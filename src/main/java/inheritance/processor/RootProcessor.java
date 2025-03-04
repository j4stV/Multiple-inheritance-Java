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
            out.println();
            
            // Конструктор
            out.println("    public " + rootClassName + "() {");
            out.println("        Mixin mixin = this.getClass().getAnnotation(Mixin.class);");
            out.println("        if (mixin != null && mixin.value().length > 0) {");
            out.println("            try {");
            out.println("                parent = mixin.value()[0].getDeclaredConstructor().newInstance();");
            out.println("            } catch (Exception e) {");
            out.println("                throw new RuntimeException(\"Не удалось создать экземпляр родительского класса\", e);");
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