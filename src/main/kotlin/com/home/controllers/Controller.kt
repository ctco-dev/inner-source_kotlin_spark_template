package com.home.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.home.context
import com.home.domain.DataRepository
import com.home.dto.Aggregate
import com.home.dto.Data
import com.home.integration.DataBaseClient
import spark.Request
import spark.Response
import spark.Spark
import java.io.IOException

class Controller(val repository: DataRepository,
                 val objectMapper: ObjectMapper = context().objectMapper,
                 val dataBaseClient: DataBaseClient = context().dbClient) {

    private fun Request.getId(): Long {
        return this.params("id").toLong()
    }

    private fun Request.getData(): Data {
        val body = this.body()
        return objectMapper.readValue(body, Data::class.java)
    }

    private fun Response.setStatusBasedOnPresence(any: Any?) {
        if (any == null) {
            this.status(404)
        } else {
            this.status(200)
        }
    }

    fun getData(request: Request, response: Response): Data? {
        val data = repository.read(request.getId())
        response.setStatusBasedOnPresence(data)
        return data
    }

    fun deleteData(request: Request, response: Response): Data? {
        val data = repository.delete(request.getId())
        response.setStatusBasedOnPresence(data)
        return data
    }


    @Throws(IOException::class)
    fun createData(request: Request, response: Response): Data {
        val data = request.getData()
        repository.create(data)
        response.status(201)
        return data
    }

    @Throws(IOException::class)
    fun updateData(request: Request, response: Response): Data {
        val id = request.getId()
        val data = request.getData()

        if (id != data.id) {
            Spark.halt(400)
        }

        val old = repository.read(id)
        val status = if (old != null) 201 else 200

        repository.update(data)
        response.status(status)
        return data
    }

    @Throws(IOException::class)
    fun getAggregate(@Suppress("UNUSED_PARAMETER") request: Request, response: Response): Aggregate {

        dataBaseClient.proceedCreateUser()
        dataBaseClient.proceedGetUsers()

        val data1 = repository.read(1)
        val data2 = repository.read(2)

        response.status(200)
        return Aggregate(setOf(data1, data2))
    }

}
