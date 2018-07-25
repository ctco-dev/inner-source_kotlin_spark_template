package com.home

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.home.integration.DataBaseClient
import com.typesafe.config.ConfigFactory
import org.apache.commons.dbcp2.BasicDataSource

fun context(): Context = Context

/**
 * Note that this is an `object`, so we have a guarantee that this is a Singleton
 */
object Context {
    private val config = ConfigFactory.load()

    private val settings: Settings = Settings(config)

    val sparkPort: Int = settings.sparkPort

    val objectMapper: ObjectMapper = ObjectMapper()
            .registerModule(ParameterNamesModule())

    private val dataSource: BasicDataSource
        get() {
            val basicDataSource = BasicDataSource()
            basicDataSource.url = settings.dbUrl
            basicDataSource.username = settings.dbUsername
            basicDataSource.password = settings.dbPassword
            basicDataSource.initialSize = 5
            return basicDataSource
        }

    val dbClient: DataBaseClient = DataBaseClient(dataSource)
}
