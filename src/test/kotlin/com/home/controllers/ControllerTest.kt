package com.home.controllers

import com.home.domain.DataRepository
import com.home.integration.DataBaseClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import spark.Request
import spark.Response

@ExtendWith(MockitoExtension::class)
class ControllerTest {
    private lateinit var controller: Controller

    @BeforeEach
    internal fun setUp() {
        controller = Controller(
            repository = DataRepository,
            dataBaseClient = mock(DataBaseClient::class.java)
        )
    }

    @Test
    fun `test getData`() {
        val mockRequest = mock(Request::class.java)
        `when`(mockRequest.params("id"))
            .thenReturn("1")

        val mockResponse = mock(Response::class.java)

        val (id, content) = controller.getData(mockRequest, mockResponse)!!

        assertEquals(1L, id)
        assertEquals("first", content)

        verify(mockResponse).status(200)
    }
}
