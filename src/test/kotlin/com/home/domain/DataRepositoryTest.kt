package com.home.domain

import com.home.dto.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class DataRepositoryTest {

    private var instance: Repository = DataRepository

    @BeforeEach
    internal fun setUp() {
        val fst = Data(1L, "first")
        val snd = Data(2L, "second")
        instance.update(fst)
        instance.update(snd)
    }

    @Test
    fun read() {
        val fst = Data(1L, "first")
        val snd = Data(2L, "second")
        assertAll(
                { assertEquals(fst, instance.read(1L)) },
                { assertEquals(snd, instance.read(2L))}
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