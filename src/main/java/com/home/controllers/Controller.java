package com.home.controllers;

import com.home.domain.DataRepository;
import com.home.dto.Aggregate;
import com.home.dto.Data;
import com.home.integration.RemoteDataClient;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Controller {

    @Inject
    private ExecutorService executorService;
    @Inject
    private DataRepository dataRepository;
    @Inject
    private RemoteDataClient remoteDataClient;


    public Data getData(Request request, Response response) {
        response.type("application/json");

        Long id = Long.parseLong(request.params("id"));
        return dataRepository.read(id);
    }

    public Data update(Request request, Response response) {
        response.type("application/json");

        Long id = Long.parseLong(request.params("id"));
        return dataRepository.read(id);
    }

    public Aggregate getAggregate(Request request, Response response) throws ExecutionException, InterruptedException {

        Future<Data> data1Future = executorService.submit(() -> remoteDataClient.getRemoteData("1", 600));
        Data data2 = remoteDataClient.getRemoteData("2", 600);

        List<Data> results = Arrays.asList(data1Future.get(), data2);
        return new Aggregate(results);
    }

}
