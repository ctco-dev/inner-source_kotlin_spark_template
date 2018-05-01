package com.home.integration;

import com.home.dto.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataService {

  @GET("data/{id}")
  Call<Data> getData(@Path("id") String id);
}