package com.home.handlers

import spark.Request
import spark.Response

val versionHandler = { _: Request, _: Response ->
    VersionResponse("Application", System.getenv("APP_VERSION") ?: "¯\\_(ツ)_/¯")
}
