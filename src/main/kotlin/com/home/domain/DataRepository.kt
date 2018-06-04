package com.home.domain

import com.home.dto.Data
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class DataRepository {
    private val store: MutableMap<Long, Data> = ConcurrentHashMap(hashMapOf(
            1L to Data(1L, "first"),
            2L to Data(2L, "second")
    ))

    fun update(data: Data): Data {
        store.put(data.id, data)
        return data
    }

    fun create(data: Data) : Data{
        val r: Random = Random()
        val newData = Data(r.nextLong(), data.content)
        return this.update(newData)
    }

    fun read(id: Long): Data? = store.get(id)

    fun delete(id: Long): Data? = store.remove(id)
}