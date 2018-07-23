package com.home.handlers

import com.home.context
import spark.Request
import spark.Response
import spark.Route


val readinessHandler = Route { _: Request, response: Response ->
    if (!context().dbClient.isConnectionAlive()) {
        response.status(500)
    }
    "ok"
}