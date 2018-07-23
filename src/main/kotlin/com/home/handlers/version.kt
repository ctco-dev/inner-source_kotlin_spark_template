package com.home.handlers

import spark.Request
import spark.Response
import spark.Route

data class VersionResponse(val version: String)

val versionHandler = Route { _: Request, _: Response ->
    VersionResponse(System.getenv("APP_VERSION") ?: "¯\\_(ツ)_/¯")
}