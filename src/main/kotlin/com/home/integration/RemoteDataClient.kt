package com.home.integration

import com.home.dto.Data
import retrofit2.Call
import retrofit2.Response
import java.util.logging.Logger

class RemoteDataClient(val dataService: DataService) {
    companion object {
        val log: Logger = Logger.getLogger(this::class.java.simpleName)
    }

    fun getRemoteData(id: String, sleep: Long): Data? {
        log.info("Data requested: $id")
        try {
            Thread.sleep(sleep)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val dataCall: Call<Data> = dataService.getData(id)
        val modelResponse: Response<Data> = dataCall.execute()
        log.info("Data retrieved: $id")
        return modelResponse.body()
    }
}