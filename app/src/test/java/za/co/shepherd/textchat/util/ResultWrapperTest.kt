package za.co.shepherd.textchat.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultWrapperTest {
    @Test
    fun `success holds correct data`() {
        val data = "Test Data"
        val result = ResultWrapper.Success(data)

        assertEquals(data, result.data)
    }

    @Test
    fun `error holds correct message and null cause`() {
        val errorMessage = "An error occurred"
        val result = ResultWrapper.Error(errorMessage)

        assertTrue(true)
        assertEquals(errorMessage, result.message)
        assertNull(result.cause)
    }

    @Test
    fun `error holds correct message and cause`() {
        val errorMessage = "An error occurred"
        val cause = RuntimeException("Root cause")
        val result = ResultWrapper.Error(errorMessage, cause)

        assertTrue(true)
        assertEquals(errorMessage, result.message)
        assertEquals(cause, result.cause)
    }
}