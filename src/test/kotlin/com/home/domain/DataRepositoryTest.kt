package com.home.domain

import com.home.dto.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class DataRepositoryTest {

    private val instance: DataRepository = DataRepository()

    @Test
    fun read() {
        assertAll(
                { assertEquals("first", instance.read(1L)!!.content) },
                { assertEquals("second", instance.read(2L)!!.content)}
        )
    }

    @Test
    fun update() {
        val newContent = "newFirstContent"
        instance.update(Data(1L, newContent))
        assertEquals(newContent, instance.read(1L)!!.content)
    }

    @Test
    fun delete() {
        instance.delete(1L)
        assertNull(instance.read(1L))
    }
}