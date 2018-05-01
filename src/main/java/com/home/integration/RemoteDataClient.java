package com.home.integration;

import com.home.dto.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Inject;
import java.io.IOException;

@Slf4j
public class RemoteDataClient {

    private final DataService service;

    @Inject
    public RemoteDataClient(Retrofit retrofit) {
        this.service = retrofit.create(DataService.class);
    }

    public Data getRemoteData(@NonNull String id, long sleep){
        log.info("data requested: {}", id);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Call<Data> dataCall = service.getData(id);
        Response<Data> modelResponse;
        try {
            modelResponse = dataCall.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("data retrieved: {}", id);
        return modelResponse.body();
    }
}
