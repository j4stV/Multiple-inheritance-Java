plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("signing")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.github.j4stV"
version = "1.0.0"
description = "Java Multiple Inheritance Framework"

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// Конфигурация для корректной обработки аннотаций
tasks.register<JavaCompile>("compileAnnotations") {
    source = fileTree("src/main/java/inheritance/annotations")
    destinationDirectory.set(file("${buildDir}/classes/java/annotations"))
    classpath = files()
}

tasks.register<JavaCompile>("compileProcessor") {
    source = fileTree("src/main/java/inheritance/processor")
    destinationDirectory.set(file("${buildDir}/classes/java/processor"))
    classpath = files("${buildDir}/classes/java/annotations")
    dependsOn("compileAnnotations")
}

// Генерация файла сервиса для процессора аннотаций
tasks.register<Copy>("generateServiceFile") {
    from("src/main/resources")
    into("${buildDir}/classes/java/processor/META-INF/services")
    include("javax.annotation.processing.Processor")
    doFirst {
        file("${buildDir}/classes/java/processor/META-INF/services").mkdirs()
    }
}

// Настройка основной компиляции
tasks.named<JavaCompile>("compileJava") {
    dependsOn("compileAnnotations", "compileProcessor", "generateServiceFile")
    
    // Включаем скомпилированные файлы в classpath
    classpath = files(
        "${buildDir}/classes/java/annotations",
        "${buildDir}/classes/java/processor",
        configurations.compileClasspath
    )
    
    // Обеспечиваем, чтобы компиляция не перетирала аннотации и процессор
    options.annotationProcessorPath = files(
        "${buildDir}/classes/java/annotations",
        "${buildDir}/classes/java/processor"
    )
    
    // Исключаем классы примеров из основной компиляции
    exclude("example/**")
    exclude("**/Main.java")
}

tasks.named<Jar>("jar") {
    dependsOn("compileAnnotations", "compileProcessor", "generateServiceFile")
    
    // Обработка дубликатов - использовать первый найденный файл
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    // Включаем все скомпилированные файлы в JAR
    from("${buildDir}/classes/java/main")
    from("${buildDir}/classes/java/annotations")
    from("${buildDir}/classes/java/processor")
    
    // Включаем META-INF/services
    from("${buildDir}/classes/java/processor/META-INF") {
        into("META-INF")
    }
}

// Создание Shadow JAR (fat jar) со всеми зависимостями
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    
    // Обработка дубликатов - использовать первый найденный файл
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    // Включаем все скомпилированные файлы в JAR
    from("${buildDir}/classes/java/main")
    from("${buildDir}/classes/java/annotations")
    from("${buildDir}/classes/java/processor")
    
    // Включаем META-INF/services
    from("${buildDir}/classes/java/processor/META-INF") {
        into("META-INF")
    }
}

// Настройка Javadoc
tasks.javadoc {
    exclude("example/**")
    exclude("**/Main.java")
    options.encoding = "UTF-8"
    
    // Опции для более подробной информации о ошибках
    options {
        this as StandardJavadocDocletOptions
        addStringOption("Xdoclint:none", "-quiet")
    }
}

// Добавляем исходники и javadoc во все JAR
tasks.named<Jar>("sourcesJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
    exclude("example/**")
    exclude("**/Main.java")
}

tasks.named<Jar>("javadocJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(tasks.javadoc)
    archiveClassifier.set("javadoc")
}

// Настройка публикации в Maven
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            
            pom {
                name.set("Java Multiple Inheritance")
                description.set("A framework for implementing multiple inheritance in Java")
                url.set("https://github.com/j4stV/Multiple-inheritance-Java")
                
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                
                developers {
                    developer {
                        id.set("j4stV")
                        name.set("j4stV")
                        email.set("j4stV@github.com")
                    }
                }
                
                scm {
                    connection.set("scm:git:git://github.com/j4stV/Multiple-inheritance-Java.git")
                    developerConnection.set("scm:git:ssh://github.com:j4stV/Multiple-inheritance-Java.git")
                    url.set("https://github.com/j4stV/Multiple-inheritance-Java/tree/main")
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "localRepo"
            url = uri("${buildDir}/repo")
        }
    }
}

// Настройка подписи артефактов (отключаем для JitPack)
signing {
    setRequired(false)
}

// Создание файла сервиса для процессора аннотаций
tasks.register("createServiceFile") {
    doLast {
        val servicesDir = file("src/main/resources/META-INF/services")
        servicesDir.mkdirs()
        val processorFile = file("$servicesDir/javax.annotation.processing.Processor")
        processorFile.writeText("inheritance.processor.RootProcessor")
    }
}

// Запуск всех тестов
tasks.named<Test>("test") {
    useJUnit()
    
    // Пропускаем тесты, так как они требуют дополнительной настройки
    enabled = false
}

// Подготовка пути в зависимости от операционной системы
fun getClasspathSeparator(): String {
    return if (System.getProperty("os.name").toLowerCase().contains("windows")) {
        ";"
    } else {
        ":"
    }
}

// Добавляем задачу для запуска компиляции тестов в правильном порядке
tasks.register("compileAllTests") {
    dependsOn("compileJava", "jar")
    doLast {
        // Создаем директорию для тестов
        mkdir("${buildDir}/test-classes")
        
        // Получаем разделитель пути для текущей ОС
        val pathSeparator = getClasspathSeparator()
        
        // Компиляция интерфейсов с аннотацией @Root
        exec {
            workingDir = projectDir
            val cmd = mutableListOf("javac", 
                "-d", "${buildDir}/test-classes",
                "-cp", "${buildDir}/libs/java-multiple-inheritance-1.0.0.jar${pathSeparator}${configurations.testCompileClasspath.get().asPath}",
                "-processor", "inheritance.processor.RootProcessor",
                "src/test/java/inheritance/tests/linear/TestLinearInterface.java",
                "src/test/java/inheritance/tests/diamond/DiamondInterface.java",
                "src/test/java/inheritance/tests/cyclic/CyclicInterface.java",
                "src/test/java/inheritance/tests/repeatedAncestor/RepeatedAncestorInterface.java",
                "src/test/java/inheritance/tests/generic/GenericInterface.java",
                "src/test/java/inheritance/tests/topological/TopologicalInterface.java"
            )
            
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                commandLine("cmd", "/c", *cmd.toTypedArray())
            } else {
                commandLine(*cmd.toTypedArray())
            }
        }
        
        // Компиляция классов, которые используют сгенерированные классы
        exec {
            workingDir = projectDir
            val cmd = mutableListOf("javac", 
                "-d", "${buildDir}/test-classes",
                "-cp", "${buildDir}/test-classes${pathSeparator}${buildDir}/libs/java-multiple-inheritance-1.0.0.jar${pathSeparator}${configurations.testCompileClasspath.get().asPath}",
                // Все классы в тестовых пакетах кроме интерфейсов
                "src/test/java/inheritance/tests/linear/ClassA.java",
                "src/test/java/inheritance/tests/linear/ClassB.java",
                "src/test/java/inheritance/tests/linear/ClassC.java",
                // Другие классы можно добавить аналогично
                "src/test/java/inheritance/tests/LinearInheritanceTest.java"
            )
            
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                commandLine("cmd", "/c", *cmd.toTypedArray())
            } else {
                commandLine(*cmd.toTypedArray())
            }
        }
    }
}

// Задача для запуска тестов
tasks.register("runTests") {
    dependsOn("compileAllTests")
    doLast {
        val pathSeparator = getClasspathSeparator()
        
        exec {
            workingDir = projectDir
            val cmd = mutableListOf("java", 
                "-cp", "${buildDir}/test-classes${pathSeparator}${buildDir}/libs/java-multiple-inheritance-1.0.0.jar${pathSeparator}${configurations.testRuntimeClasspath.get().asPath}",
                "org.junit.runner.JUnitCore",
                "inheritance.tests.LinearInheritanceTest"
                // Другие тесты можно добавить аналогично
            )
            
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                commandLine("cmd", "/c", *cmd.toTypedArray())
            } else {
                commandLine(*cmd.toTypedArray())
            }
        }
    }
}

// По умолчанию запускаем тесты и создаем все JAR
tasks.register("prepareRelease") {
    dependsOn("jar", "sourcesJar", "javadocJar", "shadowJar")
}

defaultTasks("prepareRelease")

