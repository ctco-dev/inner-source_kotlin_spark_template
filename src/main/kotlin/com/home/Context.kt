package com.home

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.home.integration.DataBaseClient
import com.home.integration.DataService
import com.home.integration.RemoteDataClient
import org.apache.commons.dbcp2.BasicDataSource

fun context(): Context = Context

/**
 * Note that this is an `object`, so we have a guarantee that this is a Singleton
 */
object Context {
    val objectMapper: ObjectMapper = ObjectMapper()
            .registerModule(ParameterNamesModule())

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
