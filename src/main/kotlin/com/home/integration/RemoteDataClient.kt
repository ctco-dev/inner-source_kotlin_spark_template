package com.home.integration

import com.home.dto.Data
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.logging.Logger

class RemoteDataClient(retrofit: Retrofit) {
    companion object {
        val log: Logger = Logger.getLogger(this::class.java.simpleName)
    }

    private val service: DataService = retrofit.create(DataService::class.java)

    fun getRemoteData(id: String, sleep: Long): Data? {
        log.info("Data requested: $id")
        try {
            Thread.sleep(sleep)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val dataCall: Call<Data> = service.getData(id)
        val modelResponse: Response<Data> = dataCall.execute()
        log.info("Data retrieved: $id")
        return modelResponse.body()
    }
}