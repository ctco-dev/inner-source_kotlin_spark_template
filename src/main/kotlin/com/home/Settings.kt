package com.home

import com.typesafe.config.Config

class Settings(conf: Config) {
    val dbUrl: String = conf.getString("DB_URL")
    val dbUsername: String = conf.getString("DB_USERNAME")
    val dbPassword: String = conf.getString("DB_PASSWORD")

    val sparkPort: Int = conf.getInt("SPARK_PORT")
    val allowOrigin: String = conf.getString("ALLOW_ORIGIN")
}