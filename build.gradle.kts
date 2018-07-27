import com.github.jengelman.gradle.plugins.shadow.internal.JavaJarExec
import kotlin.collections.listOf
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties
import java.io.FileInputStream

plugins {
    application
    kotlin("jvm") version "1.2.51"
    id("com.github.johnrengelman.shadow").version("2.0.4")
}

// get the Kotlin version from the Kotlin plugin definition
val kotlinVersion = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion
val sparkVersion = "2.7.2"
val jacksonVersion = "2.9.5"
val junitVersion = "5.2.0"
val jooqVersion = "3.11.0"

allprojects {
    repositories {
        mavenCentral()
    }
}

fun readEnvFile(filename: String): Map<String, String> {
    val p = Properties()
    val f = File(filename)
    if (f.isFile) {
        p.load(FileInputStream(f))
    }
    val mss = p as Map<String, String>
    return mss
}

fun initSystemProps(envProps: Map<String, String>) {
    envProps.forEach { envProp, envValue ->
        System.setProperty(envProp, envValue)
    }
}

val envProps = readEnvFile(".env")
initSystemProps(envProps)

dependencies {
    compile(kotlin("stdlib", kotlinVersion))
    compile(kotlin("stdlib-jdk8", kotlinVersion))

    compile(project(":dal"))

    compile(group = "com.sparkjava", name = "spark-core", version = sparkVersion)
    compile(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = jacksonVersion)
    compile(group = "com.fasterxml.jackson.module", name = "jackson-module-parameter-names", version = jacksonVersion)
    compile(group = "org.slf4j", name = "slf4j-simple", version = "1.7.25")

    compile(group = "org.apache.commons", name = "commons-dbcp2", version = "2.4.0")

    compile(group = "com.typesafe", name = "config", version = "1.3.3")

    testCompile(kotlin("stdlib", kotlinVersion))
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
    if (System.getenv("TEAMCITY_VERSION") != null) {

        addTestListener(object: TestListener {
            override fun beforeSuite(suite: TestDescriptor) {
                println("testListener: $suite")
            }
            override fun beforeTest(testDescriptor: TestDescriptor) {
                println("testListener: $testDescriptor")
            }
            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                println("testListener: $testDescriptor, $result")
            }
            override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                println("testListener: $suite, $result")
            }
        })

        // Seems to work on individual tests?
        addTestOutputListener { td: TestDescriptor, toe: TestOutputEvent ->
            println(" testOutputLister: ${td.toString()} ${toe.toString()}")
        }
    } else {
        println("oy vey")
    }
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

task("install", type = Exec::class) {
    configurations.compile.files
    commandLine = listOf("echo", "Downloaded all dependencies")
}

val shadowJar: ShadowJar by tasks
shadowJar.archiveName = "app.jar"