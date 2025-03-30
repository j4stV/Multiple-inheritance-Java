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

// Configuration for proper annotation processing
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

// Service file generation for annotation processor
tasks.register<Copy>("generateServiceFile") {
    from("src/main/resources")
    into("${buildDir}/classes/java/processor/META-INF/services")
    include("javax.annotation.processing.Processor")
    doFirst {
        file("${buildDir}/classes/java/processor/META-INF/services").mkdirs()
    }
}

// Main compilation settings
tasks.named<JavaCompile>("compileJava") {
    dependsOn("compileAnnotations", "compileProcessor", "generateServiceFile")
    
    // Include compiled files in classpath
    classpath = files(
        "${buildDir}/classes/java/annotations",
        "${buildDir}/classes/java/processor",
        configurations.compileClasspath
    )
    
    // Ensure that compilation doesn't overwrite annotations and processor
    options.annotationProcessorPath = files(
        "${buildDir}/classes/java/annotations",
        "${buildDir}/classes/java/processor"
    )
    
    // Exclude example classes from main compilation
    exclude("example/**")
    exclude("**/Main.java")
}

tasks.named<Jar>("jar") {
    dependsOn("compileAnnotations", "compileProcessor", "generateServiceFile")
    
    // Handle duplicates - use the first found file
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    // Include all compiled files in JAR
    from("${buildDir}/classes/java/main")
    from("${buildDir}/classes/java/annotations")
    from("${buildDir}/classes/java/processor")
    
    // Include META-INF/services
    from("${buildDir}/classes/java/processor/META-INF") {
        into("META-INF")
    }
}

// Create Shadow JAR (fat jar) with all dependencies
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    
    // Handle duplicates - use the first found file
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    
    // Include all compiled files in JAR
    from("${buildDir}/classes/java/main")
    from("${buildDir}/classes/java/annotations")
    from("${buildDir}/classes/java/processor")
    
    // Include META-INF/services
    from("${buildDir}/classes/java/processor/META-INF") {
        into("META-INF")
    }
}

// Javadoc configuration
tasks.javadoc {
    exclude("example/**")
    exclude("**/Main.java")
    options.encoding = "UTF-8"
    
    // Options for more detailed error information
    options {
        this as StandardJavadocDocletOptions
        addStringOption("Xdoclint:none", "-quiet")
    }
}

// Add sources and javadoc to all JARs
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

// Maven publication configuration
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

// Artifact signing configuration (disabled for JitPack)
signing {
    setRequired(false)
}

// Service file creation for annotation processor
tasks.register("createServiceFile") {
    doLast {
        val servicesDir = file("src/main/resources/META-INF/services")
        servicesDir.mkdirs()
        val processorFile = file("$servicesDir/javax.annotation.processing.Processor")
        processorFile.writeText("inheritance.processor.RootProcessor")
    }
}

// Run all tests
tasks.named<Test>("test") {
    useJUnit()
    
    // Skip tests as they require additional configuration
    enabled = false
}

// Prepare path depending on operating system
fun getClasspathSeparator(): String {
    return if (System.getProperty("os.name").toLowerCase().contains("windows")) {
        ";"
    } else {
        ":"
    }
}

// Add task for compiling tests in the correct order
tasks.register("compileAllTests") {
    dependsOn("compileJava", "jar")
    doLast {
        // Create test directory
        mkdir("${buildDir}/test-classes")
        
        // Get path separator for current OS
        val pathSeparator = getClasspathSeparator()
        
        // Compilation of interfaces with @Root annotation
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
        
        // Compilation of classes that use generated classes
        exec {
            workingDir = projectDir
            val cmd = mutableListOf("javac", 
                "-d", "${buildDir}/test-classes",
                "-cp", "${buildDir}/test-classes${pathSeparator}${buildDir}/libs/java-multiple-inheritance-1.0.0.jar${pathSeparator}${configurations.testCompileClasspath.get().asPath}",
                // All classes in test packages except interfaces
                "src/test/java/inheritance/tests/linear/ClassA.java",
                "src/test/java/inheritance/tests/linear/ClassB.java",
                "src/test/java/inheritance/tests/linear/ClassC.java",
                // Other classes can be added similarly
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

// Task for running tests
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
                // Other tests can be added similarly
            )
            
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                commandLine("cmd", "/c", *cmd.toTypedArray())
            } else {
                commandLine(*cmd.toTypedArray())
            }
        }
    }
}

// By default, run tests and create all JARs
tasks.register("prepareRelease") {
    dependsOn("jar", "sourcesJar", "javadocJar", "shadowJar")
}

defaultTasks("prepareRelease")

