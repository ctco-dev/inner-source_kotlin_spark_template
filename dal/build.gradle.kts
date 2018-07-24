import kotlin.collections.listOf
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.2.51"
val jooqVersion = "3.11.0"

val junitVersion = "5.2.0"

base {
    archivesBaseName = "spark-web-example"
}

plugins {
    distribution
    kotlin("jvm")
}

dependencies {
    compile(group = "org.postgresql", name = "postgresql", version = "42.2.2")
    compile(group = "org.jooq", name = "jooq", version = jooqVersion)

    testCompile(group = "org.jooq", name = "jooq-meta", version = jooqVersion)
    testCompile(group = "org.jooq", name = "jooq-codegen", version = jooqVersion)
    testCompile(group = "org.junit", name = "junit-bom", version = junitVersion)
    testImplementation(group = "org.junit", name = "junit-bom", version = junitVersion)
    testCompile(kotlin("stdlib", kotlinVersion))
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api")
    testRuntimeOnly(group = "org.junit.jupiter", name = "junit-jupiter-engine")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.javaParameters = true
    kotlinOptions.jvmTarget = "1.8"
}

fun getProperty(key: String): String {
    return System.getProperty(key) ?: System.getenv(key)!!
}
