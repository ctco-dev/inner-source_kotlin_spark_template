package com.home.domain;

import com.home.dto.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataRepository {

    private Map<Long, Data> store = new ConcurrentHashMap<>();

    public DataRepository() {
        store.put(1L, new Data(1L, "first"));
        store.put(2L, new Data(2L, "second"));
    }

    public void update(Data data) {
        create(data);
    }

    public void create(Data data) {
        store.put(data.getId(), data);
    }

    public Data read(Long id) {
        return store.get(id);
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
