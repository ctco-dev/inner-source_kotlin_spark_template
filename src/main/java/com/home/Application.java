package com.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.controllers.Controller;
import lombok.extern.slf4j.Slf4j;
import spark.Route;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static spark.Spark.*;

@Slf4j
public class Application {

    private final ObjectMapper objectMapper;
    private final Controller controller;

    private Application(ObjectMapper objectMapper, Controller controller) {
        this.objectMapper = objectMapper;
        this.controller = controller;
    }

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Controller controller = new Controller(objectMapper, executorService);
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
        log.info("Heap total: {}", totalHeapSize/ 1024 / 1024 + " Mb");

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
        log.info("Heap max size: {}", Runtime.getRuntime().maxMemory()/ 1024 / 1024 + " Mb");

        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
        long heapFreeSize = Runtime.getRuntime().freeMemory();
        log.info("Heap used: {}", (totalHeapSize - heapFreeSize) / 1024 / 1024 + " Mb");
    }
}