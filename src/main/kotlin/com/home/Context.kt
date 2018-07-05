package com.home

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.home.integration.DataBaseClient
import com.home.integration.DataService
import com.home.integration.RemoteDataClient
import org.jooq.DSLContext
import org.jooq.impl.DSL
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun api() : Context = Context

/**
 * Note that this is an `object`, so we have a guarantee that this is a Singleton
 */
object Context {
    val executorService: ExecutorService = Executors.newFixedThreadPool(10)

    val objectMapper: ObjectMapper = ObjectMapper()
            .registerModule(ParameterNamesModule())

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:4567/")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()

    val dataService: DataService = retrofit.create(DataService::class.java)

    val remoteDataClient: RemoteDataClient = RemoteDataClient(dataService)

    val dbConnection: Connection
        get() {
            val url = getProperty("DB_URL")
            val password = getProperty("DB_PASSWORD")
            val userName = getProperty("DB_USERNAME")

            return DriverManager.getConnection(url, userName, password)
        }

    val jooqContext: DSLContext = DSL.using(dbConnection)

    val dbClient: DataBaseClient = DataBaseClient(dbConnection);

    private fun getProperty(key: String): String? =
            System.getProperty(key) ?: io.github.cdimascio.dotenv.dotenv {directory = "."}[key]
}
