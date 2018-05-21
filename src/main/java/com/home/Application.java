package com.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.home.controllers.Controller;
import com.home.domain.DataRepository;
import com.home.integration.RemoteDataClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import spark.Route;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static spark.Spark.*;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getSimpleName());

    private final ObjectMapper objectMapper;
    private final Controller controller;

    private Application(ObjectMapper objectMapper, Controller controller) {
        this.objectMapper = objectMapper;
        this.controller = controller;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        DataRepository dataRepository = new DataRepository();

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule());

        RemoteDataClient remoteDataClient = new RemoteDataClient(new Retrofit.Builder()
                .baseUrl("http://localhost:4567/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build());

        Controller controller = new Controller(objectMapper, executorService, remoteDataClient, dataRepository);
        Application application = new Application(objectMapper, controller);
        application.start();
    }

    private void start() {
        before((request, response) -> response.type("application/json"));

        getAsJson("/aggregate", controller::getAggregate);
        getAsJson("/data/:id", controller::getData);

        put("/data/:id", controller::updateData, objectMapper::writeValueAsString);
        delete("/data/:id", controller::deleteData, objectMapper::writeValueAsString);
        post("/data", controller::createData, objectMapper::writeValueAsString);

        info();
    }

    private void getAsJson(String path, Route route) {
        get(path, route, objectMapper::writeValueAsString);
    }

    private static void info() {
        // Get current size of heap in bytes
        long totalHeapSize = Runtime.getRuntime().totalMemory();
        log.info(() -> String.format("Heap total: %s", totalHeapSize/ 1024 / 1024 + " Mb"));

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
        log.info(() -> String.format("Heap max size: %s", Runtime.getRuntime().maxMemory()/ 1024 / 1024 + " Mb"));

        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
        long heapFreeSize = Runtime.getRuntime().freeMemory();
        log.info(() -> String.format("Heap used: %s", (totalHeapSize - heapFreeSize) / 1024 / 1024 + " Mb"));
    }
}