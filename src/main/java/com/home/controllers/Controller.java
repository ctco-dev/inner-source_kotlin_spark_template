package com.home.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.domain.DataRepository;
import com.home.dto.Aggregate;
import com.home.dto.Data;
import com.home.integration.RemoteDataClient;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.inject.Inject;
import java.io.IOException;
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
    @Inject
    private ObjectMapper objectMapper;

    public Data getData(Request request, Response response) {
        response.type("application/json");

        Long id = Long.parseLong(request.params("id"));
        Data data = dataRepository.read(id);
        int status = data != null ? 200 : 404;
        response.status(status);
        return data;
    }

    public Data deleteData(Request request, Response response) {

        Long id = Long.parseLong(request.params("id"));

        Data old = dataRepository.delete(id);
        int status = old != null ? 200 : 404;
        response.status(status);
        return old;
    }

    public Data createData(Request request, Response response) throws IOException {
        String body = request.body();
        Data data = objectMapper.readValue(body, Data.class);

        dataRepository.create(data);
        response.status(201);
        return data;
    }

    public Data updateData(Request request, Response response) throws IOException {

        Long id = Long.parseLong(request.params("id"));
        String body = request.body();
        Data data = objectMapper.readValue(body, Data.class);

        if (!data.getId().equals(id)) {
            Spark.halt(400);
        }

        Data old = dataRepository.read(id);
        int status = old != null ? 201 : 200;

        dataRepository.update(data);
        response.status(status);
        return data;
    }

    public Aggregate getAggregate(Request request, Response response) throws ExecutionException, InterruptedException {

        Future<Data> data1Future = executorService.submit(() -> remoteDataClient.getRemoteData("1", 600));
        Data data2 = remoteDataClient.getRemoteData("2", 600);

        List<Data> results = Arrays.asList(data1Future.get(), data2);
        return new Aggregate(results);
    }

}
