package com.home

import com.typesafe.config.*
import java.util.logging.Logger

class Settings (
        conf: Config = ConfigFactory.load(
                ".env",
                ConfigParseOptions.defaults().setSyntax(ConfigSyntax.PROPERTIES),
                ConfigResolveOptions.defaults()
        )
) {

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