import kotlin.collections.listOf
import com.rohanprabhu.gradle.plugins.kdjooq.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.2.51"
val jooqVersion = "3.11.0"
val postgreVersion = "42.2.2"

base {
    archivesBaseName = "spark-web-example"
}

plugins {
    `java-library`
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.3.1"
    kotlin("jvm")
}

dependencies {
    compile(kotlin("stdlib", kotlinVersion))
    compile(group = "org.jooq", name = "jooq", version = jooqVersion)
    compile(group = "org.postgresql", name = "postgresql", version = postgreVersion)

    jooqGeneratorRuntime(group = "org.jooq", name = "jooq-meta", version = jooqVersion)
    jooqGeneratorRuntime(group = "org.jooq", name = "jooq-codegen", version = jooqVersion)
    jooqGeneratorRuntime(group = "org.postgresql", name = "postgresql", version = postgreVersion)
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

fun getProperty(key: String): String? {
    return System.getProperty(key) ?: System.getenv(key)
}

if (project.properties.containsKey("jooq")) {
    jooqGenerator {
        configuration("primary", project.java.sourceSets.getByName("main")) {
            configuration = jooqCodegenConfiguration {
                jdbc = jdbc {
                    url = getProperty("DB_URL")
                    username = getProperty("DB_USERNAME")
                    password = getProperty("DB_PASSWORD")
                    driver = "org.postgresql.Driver"
                    schema = "public"
                }

                generator = generator {
                    database = database {
                        excludes = "flyway_schema_history"
                        schemaVersionProvider = "select max(version) from flyway_schema_history"
                    }

                    target = target {
                        packageName = "com.home.jooq"
                        directory = "${project.projectDir}/src/main/java"
                    }
                }
            }
        }
    }
}