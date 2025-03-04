plugins {
    java
}

group = "com.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("bin")) // Подключаем скомпилированные классы
    annotationProcessor(files("bin")) // Указываем путь к скомпилированному процессору аннотаций
}


// Задача для компиляции аннотаций (Root.java, Mixin.java)
tasks.register<JavaCompile>("compileAnnotations") {
    source = fileTree("src/main/java/inheritance/annotations")
    destinationDirectory.set(file("bin"))
    classpath = files()
}

// Задача для компиляции процессора аннотаций (RootProcessor.java)
tasks.register<JavaCompile>("compileProcessor") {
    source = fileTree("src/main/java/inheritance/processor")
    destinationDirectory.set(file("bin"))
    classpath = files("bin") // Используем скомпилированные аннотации
    dependsOn("compileAnnotations") // Указываем зависимость от compileAnnotations
}

// Основная задача компиляции с использованием процессора
tasks.register<JavaCompile>("compileMain") {
    source = fileTree("src/main/java")
    destinationDirectory.set(file("bin"))
    classpath = files("bin") // Путь к скомпилированным файлам
    dependsOn("compileAnnotations", "compileProcessor") // Сначала компилируем аннотации и процессор
    options.annotationProcessorPath = files("bin") // Путь к скомпилированному процессору
    options.compilerArgs.add("-processor")
    options.compilerArgs.add("inheritance.processor.RootProcessor") // Указываем процессор
}

// Задача для создания META-INF/сервисов
tasks.register<Copy>("copyServices") {
    from("src/META-INF/services/") // Путь к сервисам
    into("bin/META-INF/services/")
}

// Задача для сборки и копирования сервисов перед запуском
tasks.register("buildAndRun") {
    dependsOn("copyServices", "compileMain")
}

// Задача для запуска приложения
tasks.register<JavaExec>("run") {
    dependsOn("buildAndRun") // Убедитесь, что задача сборки выполнена перед запуском
    mainClass.set("Main") // Указываем основной класс
    classpath = files("bin") // Указываем classpath, чтобы использовать скомпилированные файлы в bin
}

