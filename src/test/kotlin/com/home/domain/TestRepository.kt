package com.home.domain

import com.home.dto.Data
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class TestRepository : Repository {
    private val store: MutableMap<Long, Data> = ConcurrentHashMap(hashMapOf(
            1L to Data(1L, "first"),
            2L to Data(2L, "second")
    ))

    override fun update(data: Data): Unit {
        store.put(data.id, data)
    }

    override fun create(data: Data) {
        val r: Random = Random()
        data.id = r.nextLong()
        this.update(data)
    }

    override fun read(id: Long): Data? = store.get(id)

    override fun delete(id: Long): Data? = store.remove(id)
}