package za.co.shepherd.textchat.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.data.repository.MessageRepository
import za.co.shepherd.textchat.util.ResultWrapper

@ExperimentalCoroutinesApi
class SendMessageUseCaseTest {

    private lateinit var repo: MessageRepository
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var useCase: SendMessageUseCase

    private val autoReplyDelayMillis = 100L

    @Before
    fun setUp() {
        repo = mock()
        useCase = SendMessageUseCase(repo, autoReplyDelayMillis, testScope)
    }

    @Test
    fun `sendUserMessage with blank input returns error`() = testScope.runTest {
        val inputs = listOf("", " ", "   ")
        inputs.forEach { text ->
            val result = useCase.sendUserMessage(text)
            assertTrue(result is ResultWrapper.Error)
        }
        verify(repo, never()).sendMessage(any())
    }

    @Test
    fun `sendUserMessage with other text schedules default reply`() = testScope.runTest {
        val userMessageText = "  some random text  "
        val trimmedUserMessageText = "some random text"
        val expectedReplyText = "Got it: \"$trimmedUserMessageText\""
        whenever(repo.sendMessage(any())).thenReturn(ResultWrapper.Success(Unit))

        val messageCaptor = argumentCaptor<Message>()

        val result = useCase.sendUserMessage(userMessageText)
        assertTrue(result is ResultWrapper.Success)

        advanceTimeBy(autoReplyDelayMillis + 5)

        verify(repo, times(2)).sendMessage(messageCaptor.capture())

        val sentMessages = messageCaptor.allValues
        assertEquals(2, sentMessages.size)

        assertEquals(trimmedUserMessageText, messageCaptor.firstValue.text)
        assertTrue(messageCaptor.firstValue.isFromUser)

        val replyMessage = messageCaptor.secondValue
        assertEquals(expectedReplyText, replyMessage.text)
        assertFalse(replyMessage.isFromUser)
    }

    @Test
    fun `sendUserMessage when repo fails for user message returns error`() = testScope.runTest {
        val errorMessage = "Arbitrary error"
        whenever(repo.sendMessage(any())).thenReturn(ResultWrapper.Error(errorMessage))

        val result = useCase.sendUserMessage("any message")

        assertTrue(result is ResultWrapper.Error)
        assertEquals(errorMessage, (result as ResultWrapper.Error).message)

        verify(repo, times(1)).sendMessage(any())

        advanceTimeBy(autoReplyDelayMillis + 1)

        verifyNoMoreInteractions(repo)
    }

    @Test
    fun `scheduleReply handles exception from repo sendMessage`() = runTest {

        val repo = mock<MessageRepository>()
        // First call (user message) succeeds, second call (auto-reply) throws
        whenever(repo.sendMessage(any()))
            .thenReturn(ResultWrapper.Success(Unit))
            .thenThrow(RuntimeException("Repo failure"))
        val useCase = SendMessageUseCase(repo, autoReplyDelayMillis = 0L, scope = this)

        // Spy on println to verify error logging (optional, or check for no crash)
        useCase.sendUserMessage("hello")
        // No assertion needed if just verifying no crash; for println, use a logging framework or redirect output
    }

    @Test
    fun `should delete message successfully`() = testScope.runTest {
        val message = Message(text = "Test message", isFromUser = true)
        whenever(repo.deleteMessage(message)).thenReturn(ResultWrapper.Success(Unit))

        val result = useCase.deleteMessage(message)

        assertTrue(result is ResultWrapper.Success)
        verify(repo, times(1)).deleteMessage(message)
    }
}