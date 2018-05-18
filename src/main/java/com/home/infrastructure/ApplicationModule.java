package com.home.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationModule extends AbstractModule {

    @Provides
    public ExecutorService executor() {
        return Executors.newFixedThreadPool(10);
    }

    @Provides
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
