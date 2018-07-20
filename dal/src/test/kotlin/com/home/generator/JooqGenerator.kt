package com.home.generator

import org.jooq.util.GenerationTool
import org.jooq.util.jaxb.*
import org.jooq.util.jaxb.Target
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.util.*

class JooqGenerator {

    @Test
    @Disabled
    fun `generate jOOQ sources`() {
        val configuration = Configuration()
        configuration.applyConfiguration(readEnvFile("../.env"))
        GenerationTool().run(configuration)
    }

    private fun Configuration.applyConfiguration(props: Map<String, String>) {
        withJdbc(Jdbc()
                .withDriver("org.postgresql.Driver")
                .withUrl(props["DB_URL"])
                .withSchema("public")
                .withUser(props["DB_USERNAME"])
                .withPassword(props["DB_PASSWORD"])
        ).withGenerator(Generator()
                .withDatabase(Database()
                        .withExcludes("flyway_schema_history")
                        .withSchemaVersionProvider("select max(version) from flyway_schema_history")
                )
                .withName("org.jooq.util.JavaGenerator")
                .withTarget(Target()
                        .withDirectory("./src/main/java")
                        .withPackageName("com.home.jooq")
                        .withEncoding("UTF-8")
                )
        )
    }


    fun readEnvFile(filename: String): Map<String, String> {
        val f = File(filename)
        if (f.isFile) {
            val p = Properties()
            p.load(FileInputStream(f))
            @Suppress("UNCHECKED_CAST")
            return p as Map<String, String>
        } else {
            throw NoSuchFileException(f)
        }
    }
}
