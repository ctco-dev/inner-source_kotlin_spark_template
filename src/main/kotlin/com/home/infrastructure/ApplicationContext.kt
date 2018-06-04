package com.home.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Provides
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ApplicationContext : AbstractModule() {

    @Provides
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
                .build()
    }

    @Provides
    fun executor(): ExecutorService {
        return Executors.newFixedThreadPool(10)
    }

    @Provides
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerKotlinModule()
    }
}