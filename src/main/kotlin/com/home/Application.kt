package com.home

import com.fasterxml.jackson.databind.ObjectMapper
import com.home.controllers.Controller
import com.home.domain.DataRepository
import com.home.handlers.healthHandler
import com.home.handlers.optionsHandler
import com.home.handlers.readinessHandler
import com.home.handlers.versionHandler
import spark.Filter
import spark.Request
import spark.Response
import spark.Route
import spark.Spark.*
import java.util.logging.Logger

class Application(val controller: Controller,
                  val objectMapper: ObjectMapper = context().objectMapper) {
    companion object {
        val log: Logger = Logger.getLogger(this::class.java.simpleName)
    }

    internal fun start() {
        //TODO: make the port configurable
        port(4567)

        before(Filter { _, response: Response ->
            response.type("application/json")
            response.header("Access-Control-Allow-Origin", "*")
        })

        options("/*", optionsHandler)

        // Health, readiness and version endpoints
        get("/health", healthHandler)
        get("/readiness", route(readinessHandler))
        get("/version", route(versionHandler))

        get("/aggregate", route(controller::getAggregate))
        get("/data/:id", route(controller::getData))

        put("/data/:id", route(controller::updateData))
        delete("/data/:id", route(controller::deleteData))
        post("/data", route(controller::createData))

        info()
    }

    private fun info() {
        // Get current size of heap in bytes
        val totalHeapSize = Runtime.getRuntime().totalMemory()
        log.info { String.format("Heap total: %s", (totalHeapSize / 1024 / 1024).toString() + " Mb") }

        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
        log.info { String.format("Heap max size: %s", (Runtime.getRuntime().maxMemory() / 1024 / 1024).toString() + " Mb") }

        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
        val heapFreeSize = Runtime.getRuntime().freeMemory()
        log.info { String.format("Heap used: %s", ((totalHeapSize - heapFreeSize) / 1024 / 1024).toString() + " Mb") }
    }

    private fun route(handler: (Request, Response) -> Any?): Route {
        return Route { request, response ->
            response.type("application/json")
            val result = handler(request!!, response!!)
            objectMapper.writeValueAsString(result)
        }
    }
}

fun main(args: Array<String>) {
    val controller = Controller(DataRepository)
    val application = Application(controller)
    application.start()
}
