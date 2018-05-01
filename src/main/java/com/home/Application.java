package com.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.facade.Facade;
import lombok.extern.slf4j.Slf4j;

import static spark.Spark.get;

@Slf4j
public class Application {


    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        Facade facade = new Facade();

        get("/aggregate", facade::getAggregate, objectMapper::writeValueAsString);
        get("/data/:id", facade::getData, objectMapper::writeValueAsString);

        info();
    }


    private static void info() {
        // Get current size of heap in bytes
        long heapSize = Runtime.getRuntime().totalMemory();

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
        long heapMaxSize = Runtime.getRuntime().maxMemory();

        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
        long heapFreeSize = Runtime.getRuntime().freeMemory();
        log.info("Heap used: {}", (heapSize - heapFreeSize)/1024/1024+" Mb");
    }
}