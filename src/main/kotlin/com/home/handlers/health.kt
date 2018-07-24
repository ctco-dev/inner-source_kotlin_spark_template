package com.home.handlers

import spark.Request
import spark.Response

val healthHandler = { _: Request, _: Response -> "ok" }