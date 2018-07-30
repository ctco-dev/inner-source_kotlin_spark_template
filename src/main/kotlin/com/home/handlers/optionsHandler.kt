package com.home.handlers

import spark.Request
import spark.Response

val optionsHandler = { request: Request, response: Response ->
    val accessControlRequestHeaders = request.headers("Access-Control-Request-Headers")
    if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
    }

    val accessControlRequestMethod = request.headers("Access-Control-Request-Method")
    if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod)
    }

    "OK"
}