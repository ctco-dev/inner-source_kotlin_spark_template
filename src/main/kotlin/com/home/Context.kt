package com.home

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.home.integration.DataBaseClient
import com.home.integration.DataService
import com.home.integration.RemoteDataClient
import org.apache.commons.dbcp2.BasicDataSource
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun api(): Context = Context

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

    private val dataSource: BasicDataSource
        get() {
            val basicDataSource = BasicDataSource()
            basicDataSource.url = getProperty("DB_URL")
            basicDataSource.username = getProperty("DB_USERNAME")
            basicDataSource.password = getProperty("DB_PASSWORD")
            basicDataSource.initialSize = 5
            return basicDataSource
        }

    val dbClient: DataBaseClient = DataBaseClient(dataSource);

    /**
     * First see if a Java system property is set, fallback to Environment property
     * This is done to make overrides work via -D jvm args
     */
    private fun getProperty(key: String): String = System.getProperty(key) ?: System.getenv(key)!!
}
