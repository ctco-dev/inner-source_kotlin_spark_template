package com.home.handlers

import com.home.context
import spark.Request
import spark.Response

val readinessHandler = { _: Request, _: Response ->
    VersionResponse("Database", context().dbClient.getDatabaseVersion())
}