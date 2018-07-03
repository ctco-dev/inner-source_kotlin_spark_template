package com.home.integration;

import com.example.jooq.tables.User
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

class DataBaseClient(val dbConnection: Connection) {

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
            val insertResult = dslContext.insertInto(User.USER,
                    User.USER.FIRSTNAME, User.USER.LASTNAME)
                    .values("Valerij", "Meladze")
                    .execute()
        }
    }

    fun executeQuery(function: (DSLContext) -> Unit) {
        DSL.using(dbConnection, SQLDialect.POSTGRES).use(function)
    }

}
