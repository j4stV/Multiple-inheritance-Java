plugins {
    id("java")
    id("application")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.j4stV:Multiple-inheritance-Java:v1.0.1")
    annotationProcessor("com.github.j4stV:Multiple-inheritance-Java:v1.0.1")
    
    testImplementation("junit:junit:4.13.2")
}

application {
    mainClass.set("com.example.Main")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf(
        "-encoding", "UTF-8",
        "-processor", "inheritance.processor.RootProcessor"
    ))
} 