import kotlin.collections.listOf
import com.rohanprabhu.gradle.plugins.kdjooq.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.2.50"
val jooqVersion = "3.11.0"

base {
    archivesBaseName = "sparsgfdsgk-web-example"
}

plugins {
    `java-library`
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.3.1"
    kotlin("jvm")
}

dependencies {
    compile(kotlin("stdlib", kotlinVersion))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.5")

    compile(group = "org.jooq", name = "jooq", version = jooqVersion)

    jooqGeneratorRuntime(group = "org.jooq", name = "jooq-meta", version = jooqVersion)
    jooqGeneratorRuntime(group = "org.jooq", name = "jooq-codegen", version = jooqVersion)
    jooqGeneratorRuntime("org.postgresql:postgresql:42.2.2")
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

val ENV = System.getenv()

jooqGenerator {
    configuration("primary", project.java.sourceSets.getByName("main")) {
        configuration = jooqCodegenConfiguration {
            jdbc = jdbc {
                url = "${ENV["DB_URL"]}"
                username = "${ENV["DB_USERNAME"]}"
                password = "${ENV["DB_PASSWORD"]}"
                driver = "org.postgresql.Driver"
                schema = "public"
            }

            generator = generator {
                target = target {
                    // TODO: package name should be better
                    packageName = "com.example.jooq"
                    directory = "${project.buildDir}/generated/jooq/primary"
                }
            }
        }
    }
}
