package com.home.integration

import com.home.dto.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DataService {
    @GET("data/{id}")
    fun getData(@Path("id") id: String): Call<Data>
}