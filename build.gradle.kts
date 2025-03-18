plugins {
    java
}

group = "com.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("bin")) // Connect compiled classes
    annotationProcessor(files("bin")) // Specify path to compiled annotation processor
}


// Task for compiling annotations (Root.java, Mixin.java)
tasks.register<JavaCompile>("compileAnnotations") {
    source = fileTree("src/main/java/inheritance/annotations")
    destinationDirectory.set(file("bin"))
    classpath = files()
}

// Task for compiling annotation processor (RootProcessor.java)
tasks.register<JavaCompile>("compileProcessor") {
    source = fileTree("src/main/java/inheritance/processor")
    destinationDirectory.set(file("bin"))
    classpath = files("bin") // Use compiled annotations
    dependsOn("compileAnnotations") // Specify dependency on compileAnnotations
}

// Main compilation task using processor
tasks.register<JavaCompile>("compileMain") {
    source = fileTree("src/main/java")
    destinationDirectory.set(file("bin"))
    classpath = files("bin") // Path to compiled files
    dependsOn("compileAnnotations", "compileProcessor") // First compile annotations and processor
    options.annotationProcessorPath = files("bin") // Path to compiled processor
    options.compilerArgs.add("-processor")
    options.compilerArgs.add("inheritance.processor.RootProcessor") // Specify processor
}

// Task for creating META-INF/services
tasks.register<Copy>("copyServices") {
    from("src/META-INF/services/") // Path to services
    into("bin/META-INF/services/")
}

// Task for building and copying services before running
tasks.register("buildAndRun") {
    dependsOn("compileMain", "copyServices")
}

// Cleaning task
tasks.register<Delete>("cleanAll") {
    delete("bin")
}

// Default task
defaultTasks("buildAndRun")

