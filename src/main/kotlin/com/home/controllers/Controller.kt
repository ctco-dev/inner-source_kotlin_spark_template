package com.home.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.home.api
import com.home.domain.DataRepository
import com.home.dto.Aggregate
import com.home.dto.Data
import com.home.integration.RemoteDataClient
import spark.Request
import spark.Response
import spark.Spark
import java.io.IOException
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService

class Controller(val repository: DataRepository,
                 val objectMapper: ObjectMapper = api().objectMapper,
                 val remoteDataClient: RemoteDataClient = api().remoteDataClient,
                 val executorService: ExecutorService = api().executorService) {

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

    @Throws(ExecutionException::class, InterruptedException::class)
    fun getAggregate(request: Request, response: Response): Aggregate {

        val data1Future = executorService.submit<Data> { remoteDataClient.getRemoteData("1", 600) }
        val data2 = remoteDataClient.getRemoteData("2", 600)

        val results = setOf(data1Future.get(), data2!!)
        return Aggregate(results)
    }

}
