package com.home

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.home.integration.DataService
import com.home.integration.RemoteDataClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
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
}
