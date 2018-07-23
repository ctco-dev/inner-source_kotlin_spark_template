package com.home.transformers

import com.fasterxml.jackson.databind.ObjectMapper
import spark.ResponseTransformer

class JsonTransformer : ResponseTransformer {
    companion object {
        val objectMapper = ObjectMapper()
    }

    override fun render(model: Any?): String {
        return objectMapper.writeValueAsString(model)
    }
}