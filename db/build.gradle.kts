plugins {
    //    application
    kotlin("jvm")
    id("org.flywaydb.flyway") version ("5.1.3")
}

dependencies {
    compile(kotlin("stdlib", embeddedKotlinVersion))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.5")
    compile("org.postgresql:postgresql:42.2.2")
}

fun getProperty(key: String): String {
    return System.getProperty(key) ?: System.getenv(key)!!
}

flyway {
    url = getProperty("DB_URL")
    user = getProperty("DB_USERNAME")
    password = getProperty("DB_PASSWORD")
}