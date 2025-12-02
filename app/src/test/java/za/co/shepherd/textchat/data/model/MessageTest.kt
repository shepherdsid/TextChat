package za.co.shepherd.textchat.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.UUID

class MessageTest {

    @Test
    fun `test Message creation With default Values`() {
        val messageText = "Hello, World!"
        val fromUser = true
        val message = Message(text = messageText, isFromUser = fromUser)

        assertNotNull(message.id)
        // Check if the ID is a valid UUID
        try {
            UUID.fromString(message.id)
        } catch (e: IllegalArgumentException) {
            assertTrue("ID should be a valid UUID string", false)
        }

        assertEquals(messageText, message.text)
        assertEquals(fromUser, message.isFromUser)
        assertTrue(System.currentTimeMillis() >= message.timestamp)
    }

    @Test
    fun `test Message Creation With All Values Provided`() {
        val id = "test-id"
        val messageText = "Custom message"
        val fromUser = false
        val timestamp = 1234567890L

        val message = Message(
            id = id,
            text = messageText,
            isFromUser = fromUser,
            timestamp = timestamp
        )

        assertEquals(id, message.id)
        assertEquals(messageText, message.text)
        assertEquals(fromUser, message.isFromUser)
        assertEquals(timestamp, message.timestamp)
    }
}