package com.home.integration;

import com.home.jooq.tables.User
import org.apache.commons.dbcp2.BasicDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

open class DataBaseClient(private val dataSource: BasicDataSource) {

    fun proceedGetUsers() {
        executeQuery { dslContext ->
            val result = dslContext.select().from(User.USER).fetch()
            println(result)
            result.forEach {
                println("The element is $it")
            }
        }
    }

    fun proceedCreateUser() {
        executeQuery { dslContext ->
            dslContext.insertInto(User.USER,
                    User.USER.FIRSTNAME, User.USER.LASTNAME)
                    .values("Valerij", "Meladze")
                    .execute()
        }
    }

    private fun executeQuery(function: (DSLContext) -> Unit) {
        dataSource.connection.use { dbConnection ->
            DSL.using(dbConnection, SQLDialect.POSTGRES).use(function)
        }
    }

    @Throws(Exception::class)
    fun getDatabaseVersion(): String {
        return dataSource.connection.use { dbConnection ->
            val rs = dbConnection.createStatement().executeQuery("select max(version) as version from flyway_schema_history")
            if (!rs.next()) {
                throw IllegalStateException()
            }
            rs.getString("version")
        }
    }

}
