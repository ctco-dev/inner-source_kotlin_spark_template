package com.home

import com.typesafe.config.Config
import java.util.logging.Logger

class Settings (conf: Config) {
    companion object {
        val log = Logger.getLogger(this::class.java.simpleName)
    }

    val dbUrl: String = conf.getString("DB_URL")
    val dbUsername: String = conf.getString("DB_USERNAME")
    val dbPassword: String = conf.getString("DB_PASSWORD")

    init {
        log.info(conf.toString())
    }
}