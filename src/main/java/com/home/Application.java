package com.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.home.controllers.Controller;
import com.home.infrastructure.ApplicationModule;
import lombok.extern.slf4j.Slf4j;
import spark.Route;

import javax.inject.Inject;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

@Slf4j
public class Application {

    @Inject
    private Controller controller;
    @Inject
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        Application application = injector.getInstance(Application.class);
        application.start();
    }

    public void start() {
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