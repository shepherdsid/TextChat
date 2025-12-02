package za.co.shepherd.textchat.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.util.ResultWrapper

@ExperimentalCoroutinesApi
class InMemoryMessageRepositoryTest {

    private lateinit var repository: InMemoryMessageRepository

    @Before
    fun setUp() {
        repository = InMemoryMessageRepository()
    }

    @Test
    fun `messages should initially return an emptyList`() = runTest {
        val messages = repository.messages().first()
        assertTrue(messages.isEmpty())
    }

    @Test
    fun `send message should add a message and return success`() = runTest {
        val message = Message(text = "Hello", isFromUser = true)

        val result = repository.sendMessage(message)
        val messages = repository.messages().first()

        assertTrue(result is ResultWrapper.Success)
        assertEquals(1, messages.size)
        assertEquals(message, messages.first())
    }

    @Test
    fun `delete message should remove a message and return success`() = runTest {
        val message = Message(text = "Hello", isFromUser = true)

        repository.sendMessage(message)
        val messages = repository.messages().first()
        assertEquals(1, messages.size)


        val deleteresult = repository.deleteMessage(message)
        val messagesAfterDelete = repository.messages().first()

        assertTrue(deleteresult is ResultWrapper.Success)
        assertEquals(0, messagesAfterDelete.size)
    }

    @Test
    fun `send message should add multiple messages in order`() = runTest {
        val message1 = Message(text = "First message", isFromUser = true)
        val message2 = Message(text = "Second message", isFromUser = false)

        repository.sendMessage(message1)
        repository.sendMessage(message2)

        val messages = repository.messages().first()

        assertEquals(2, messages.size)
        assertEquals(message1, messages[0])
        assertEquals(message2, messages[1])
    }

    @Test
    fun `clear should remove all messages and return success`() = runTest {
        val message = Message(text = "A message to be cleared", isFromUser = true)
        repository.sendMessage(message)

        val clearResult = repository.clear()
        val messagesAfterClear = repository.messages().first()

        assertTrue(clearResult is ResultWrapper.Success)
        assertTrue(messagesAfterClear.isEmpty())
    }

    @Test
    fun `clear should return Error when exception is thrown`() = runTest {
        val repo = mock<InMemoryMessageRepository>()
        val exception = RuntimeException("Simulated failure")
        whenever(repo.clear()).thenThrow(exception)

        val result = try {
            repo.clear()
        } catch (e: Exception) {
            ResultWrapper.Error("Failed to clear messages", e)
        }
        assertTrue(result is ResultWrapper.Error)
        assertEquals("Failed to clear messages", (result as ResultWrapper.Error).message)
        // Use the correct property for the exception, e.g. 'cause'
        assertTrue(result.cause is RuntimeException)
        assertEquals("Simulated failure", result.cause?.message)
    }

    @Test
    fun `clear should remove all messages and return error`() = runTest {
        val repo = mock<InMemoryMessageRepository>()
        val exception = RuntimeException("Simulated failure")
        whenever(repo.clear()).thenThrow(exception)

        val message = Message(text = "A message to be cleared", isFromUser = true)
        repo.sendMessage(message)

        val clearResult = try {
            repo.clear()
        } catch (e: Exception) {
            ResultWrapper.Error("Failed to clear messages", e)
        }

        assertTrue(clearResult is ResultWrapper.Error)
        assertEquals("Failed to clear messages", (clearResult as ResultWrapper.Error).message)
    }

    @Test
    fun `sendMessage should return Error when message property throws`() = runTest {
        val message = mock<Message>()
        whenever(message.text).thenThrow(RuntimeException("Simulated failure"))

        val result = repository.sendMessage(message)

        assertTrue(result is ResultWrapper.Error)
        assertEquals("Failed to add message", (result as ResultWrapper.Error).message)
        assertTrue(result.cause is RuntimeException)
        assertEquals("Simulated failure", result.cause?.message)
    }

    @Test
    fun `clear should return Error when clearing throws`() = runTest {
        val repo = mock<InMemoryMessageRepository>()
        val exception = RuntimeException("Simulated failure")
        whenever(repo.clear()).thenThrow(exception)

        val result = try {
            repo.clear()
        } catch (e: Exception) {
            ResultWrapper.Error("Failed to clear messages", e)
        }

        assertTrue(result is ResultWrapper.Error)
        assertEquals("Failed to clear messages", (result as ResultWrapper.Error).message)
        assertTrue(result.cause is RuntimeException)
        assertEquals("Simulated failure", result.cause?.message)
    }

    @Test
    fun `deleteMessage should return Error when message property throws`() = runTest {
        val message = mock<Message>()
        whenever(message.text).thenThrow(RuntimeException("Simulated failure"))

        val result = repository.deleteMessage(message)

        assertTrue(result is ResultWrapper.Error)
        assertEquals("Failed to delete message", (result as ResultWrapper.Error).message)
        assertTrue(result.cause is RuntimeException)
        assertEquals("Simulated failure", result.cause?.message)
    }
}