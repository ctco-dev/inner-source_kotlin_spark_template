package com.home.facade;

import com.home.dto.Aggregate;
import com.home.dto.Data;
import com.home.integration.Client;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Facade {

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    public Data getData(Request request, Response response) {
        response.type("application/json");

        String id = request.params("id");
        return new Data("data" + id);
    }


    public Aggregate getAggregate(Request request, Response response) throws ExecutionException, InterruptedException {
        Client client = new Client();
        Future<Data> data1Future = executorService.submit(() -> client.getData("a", 600));
        Data data2 = client.getData("b", 600);

        List<Data> results = Arrays.asList(data1Future.get(), data2);
        return new Aggregate(results);
    }

}
