import kotlin.collections.listOf
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.2.41"
val junitVersion = "5.2.0"

plugins {
    application
    kotlin("jvm") version "1.2.41"
//    id("com.github.rengelman.shadow").version("2.0.4")
    id("com.github.johnrengelman.shadow").version("2.0.4")
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib", kotlinVersion))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.5")

    compile(group = "com.sparkjava", name = "spark-core", version = "2.7.2")
    compile(group = "com.squareup.retrofit2", name = "retrofit", version = "2.4.0")
    compile(group = "com.squareup.retrofit2", name = "converter-jackson", version = "2.4.0")
    compile(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.9.5")
    compile(group = "com.fasterxml.jackson.module", name = "jackson-module-parameter-names", version = "2.9.5")
    compile(group = "org.slf4j", name = "slf4j-simple", version = "1.7.25")

    testCompile(group = "org.jetbrains.kotlin", name = "kotlin-stdlib", version = kotlinVersion)
    testImplementation(group = "org.junit", name = "junit-bom", version = junitVersion)
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api")
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine")
    testCompile(group = "org.mockito", name = "mockito-junit-jupiter", version = "2.18.3")
}

application {
    group = "lv.ctco.spark"
    version = "1.0-SNAPSHOT"
    applicationName = "spark-web-example"
    mainClassName = "com.home.ApplicationKt"
    applicationDefaultJvmArgs = listOf("-Xmx10m")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.javaParameters = true
    kotlinOptions.jvmTarget = "1.8"
}

val shadowJar: ShadowJar by tasks
shadowJar.archiveName = "app.jar"
