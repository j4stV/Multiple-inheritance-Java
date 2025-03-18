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
                messager.printMessage(Diagnostic.Kind.ERROR, "Only interfaces can be marked with @Root annotation", element);
                continue;
            }

            TypeElement interfaceElement = (TypeElement) element;
            String packageName = elementUtils.getPackageOf(interfaceElement).getQualifiedName().toString();
            String interfaceName = interfaceElement.getSimpleName().toString();
            String rootClassName = interfaceName + "Root";

            try {
                generateRootClass(packageName, interfaceName, rootClassName, interfaceElement);
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Error generating class: " + e.getMessage(), element);
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
        
        // Get interface type parameters
        List<? extends TypeParameterElement> typeParameters = interfaceElement.getTypeParameters();
        
        try (PrintWriter out = new PrintWriter(javaFile.openWriter())) {
            // Create file header
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
            out.println(" * Automatically generated root class for interface " + interfaceName);
            if (!typeParameters.isEmpty()) {
                out.println(" * @param <" + formatTypeParameters(typeParameters) + "> Type parameters");
            }
            out.println(" */");
            
            // Create class declaration with type parameters if present
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

            // Field for storing parent object
            out.println("    protected Object parent;");
            out.println();
            
            // Add static field for tracking recursion depth
            out.println("    private static final int MAX_CALL_DEPTH = 20;");
            out.println("    private static final ThreadLocal<Integer> callDepth = new ThreadLocal<Integer>() {");
            out.println("        @Override");
            out.println("        protected Integer initialValue() {");
            out.println("            return 0;");
            out.println("        }");
            out.println("    };");
            out.println();

            // Empty constructor without initialization logic
            out.println("    public " + rootClassName + "() {");
            out.println("        // Initialization is performed by MixinFactory");
            out.println("    }");
            out.println();

            // Static method for creating instances through the factory
            out.println("    /**");
            out.println("     * Creates an instance of the class through MixinFactory");
            out.println("     * @param clazz Class to create an instance of");
            out.println("     * @param <T> Type of instance being created");
            out.println("     * @return Configured instance of the class");
            out.println("     */");
            out.println("    public static <T> T createInstance(Class<T> clazz) {");
            out.println("        return MixinFactory.createInstance(clazz);");
            out.println("    }");
            out.println();

            // For each method in the interface, create a nextXXX method
            for (Element methodElement : enclosedElements) {
                ExecutableElement method = (ExecutableElement) methodElement;
                String methodName = method.getSimpleName().toString();
                String nextMethodName = "next" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);

                // Get parameter types and return type
                String returnType = method.getReturnType().toString();
                
                List<? extends VariableElement> parameters = method.getParameters();
                String parameterList = parameters.stream()
                    .map(p -> p.asType() + " " + p.getSimpleName())
                    .collect(Collectors.joining(", "));

                String parameterNames = parameters.stream()
                    .map(p -> p.getSimpleName().toString())
                    .collect(Collectors.joining(", "));

                // Generate nextXXX method with call depth checking
                out.println("    protected " + returnType + " " + nextMethodName + "(" + parameterList + ") {");
                out.println("        if (parent == null) {");
                if (!returnType.equals("void")) {
                    out.println("            return " + getDefaultReturnValue(returnType) + ";");
                } else {
                    out.println("            return;");
                }
                out.println("        }");
                out.println();
                
                // Check and increase call depth counter
                out.println("        int currentDepth = callDepth.get();");
                out.println("        if (currentDepth >= MAX_CALL_DEPTH) {");
                out.println("            System.out.println(\"Maximum method call depth exceeded: \" + MAX_CALL_DEPTH + \". Possible cycle detected.\");");
                if (!returnType.equals("void")) {
                    out.println("            return " + getDefaultReturnValue(returnType) + ";");
                } else {
                    out.println("            return;");
                }
                out.println("        }");
                out.println("        callDepth.set(currentDepth + 1);");
                out.println();
                
                out.println("        try {");
                
                // For generics use a different approach to get the method
                if (parameters.isEmpty()) {
                    out.println("            Method method = parent.getClass().getMethod(\"" + methodName + "\");");
                } else {
                    out.println("            // Create array of parameter types");
                    out.println("            Class<?>[] paramTypes = new Class<?>[" + parameters.size() + "];");
                    
                    // For each parameter determine its type
                    for (int i = 0; i < parameters.size(); i++) {
                        VariableElement param = parameters.get(i);
                        String paramType = param.asType().toString();
                        
                        // Handle type parameters
                        if (isTypeVariable(paramType, typeParameters)) {
                            out.println("            // For generics use Object.class");
                            out.println("            paramTypes[" + i + "] = Object.class;");
                        } else {
                            // For primitive types and regular classes
                            out.println("            paramTypes[" + i + "] = " + getClassForType(paramType) + ";");
                        }
                    }
                    
                    out.println("            Method method = parent.getClass().getMethod(\"" + methodName + "\", paramTypes);");
                }

                if (!returnType.equals("void")) {
                    out.println("            " + returnType + " result = (" + returnType + ") method.invoke(parent" + (parameterNames.isEmpty() ? "" : ", " + parameterNames) + ");");
                    out.println("            callDepth.set(currentDepth); // Restore counter");
                    out.println("            return result;");
                } else {
                    out.println("            method.invoke(parent" + (parameterNames.isEmpty() ? "" : ", " + parameterNames) + ");");
                    out.println("            callDepth.set(currentDepth); // Restore counter");
                }

                out.println("        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {");
                out.println("            callDepth.set(currentDepth); // Restore counter in case of error");
                out.println("            throw new RuntimeException(\"Error calling parent class method\", e);");
                out.println("        }");
                out.println("    }");
                out.println();
            }

            out.println("}");
        }
    }
    
    /**
     * Checks if a type is a type parameter from the parameter list
     * 
     * @param typeName Type name to check
     * @param typeParameters List of type parameters
     * @return true if the type is a type parameter
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
     * Returns a string to get the Class object for the given type
     * 
     * @param typeName Type name
     * @return String to get the Class
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
                // Handle parameterized types
                if (typeName.contains("<")) {
                    // For generics take only base type
                    String baseType = typeName.substring(0, typeName.indexOf('<'));
                    return baseType + ".class";
                }
                // For regular classes
                return typeName + ".class";
        }
    }
    
    /**
     * Formats type parameters for use in class declaration
     * 
     * @param typeParameters List of type parameters
     * @return String with formatted type parameters
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

    /**
     * Gets default return value for type
     * 
     * @param typeName Type name
     * @return Default value for the type
     */
    private String getDefaultReturnValue(String typeName) {
        switch (typeName) {
            case "boolean": return "false";
            case "byte": 
            case "short":
            case "int": return "0";
            case "long": return "0L";
            case "float": return "0.0f";
            case "double": return "0.0";
            case "char": return "'\\0'";
            case "java.lang.String":
            case "String": return "\"\"";
            default:
                // For reference types return null
                return "null";
        }
    }
} 