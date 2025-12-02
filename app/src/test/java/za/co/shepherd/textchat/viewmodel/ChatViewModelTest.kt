package za.co.shepherd.textchat.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.data.repository.MessageRepository
import za.co.shepherd.textchat.domain.SendMessageUseCase
import za.co.shepherd.textchat.util.ResultWrapper

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var messageRepository: MessageRepository
    private lateinit var sendMessageUseCase: SendMessageUseCase

    private val messagesFlow = MutableStateFlow<List<Message>>(emptyList())

    private lateinit var mockChatViewModel: ChatViewModel

    @Before
    fun setUp() {
        messageRepository = mock(MessageRepository::class.java)
        sendMessageUseCase = mock(SendMessageUseCase::class.java)

        whenever(messageRepository.messages()).thenReturn(messagesFlow)

        mockChatViewModel = ChatViewModel(messageRepository, sendMessageUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        val uiState = mockChatViewModel.uiState.value
        assertTrue(uiState.messages.isEmpty())
        assertNull(uiState.errorMessage)
        assertEquals("", mockChatViewModel.currentMessageText.value)
    }

    @Test
    fun `onMessageTextChanged updates currentMessageText`() = runTest {
        val newText = "Test message"
        mockChatViewModel.onMessageTextChanged(newText)
        assertEquals(newText, mockChatViewModel.currentMessageText.value)
    }

    @Test
    fun `send clears currentMessageText on success`() = runTest {
        whenever(sendMessageUseCase.sendUserMessage("Test message"))
            .thenReturn(ResultWrapper.Success(Unit))

        mockChatViewModel.send("Test message")

        advanceUntilIdle()
        assertEquals("", mockChatViewModel.currentMessageText.value)
        assertNull(mockChatViewModel.uiState.value.errorMessage)
    }

    @Test
    fun `send sets errorMessage on failure`() = runTest {
        val errorMessage = "Failed to send message"
        whenever(sendMessageUseCase.sendUserMessage("Test message"))
            .thenReturn(ResultWrapper.Error(errorMessage))

        mockChatViewModel.send("Test message")

        advanceUntilIdle()
        assertEquals(errorMessage, mockChatViewModel.uiState.value.errorMessage)
    }

    @Test
    fun `clearError clears errorMessage`() = runTest {
        mockChatViewModel.send("Test message")
        mockChatViewModel.clearError()

        advanceUntilIdle()
        assertNull(mockChatViewModel.uiState.value.errorMessage)
    }

    @Test
    fun `uiState updates when repository emits new messages`() = runTest {
        val initialMessages = listOf(Message("1", "Hello", true, 123456789))
        val updatedMessages = listOf(
            Message("1", "Hello", true, 123456789),
            Message("2", "World", false, 123456790)
        )

        messagesFlow.value = initialMessages
        advanceUntilIdle()
        assertEquals(initialMessages, mockChatViewModel.uiState.value.messages)

        messagesFlow.value = updatedMessages
        advanceUntilIdle()
        assertEquals(updatedMessages, mockChatViewModel.uiState.value.messages)
    }

    @Test
    fun `should able to delete a message` () = runTest {
        val messageToDelete = Message("1", "Hello", true, 123456789)
        whenever(sendMessageUseCase.deleteMessage(messageToDelete))
            .thenReturn(ResultWrapper.Success(Unit))

        val result = mockChatViewModel.deleteMessage(messageToDelete)
        advanceUntilIdle()

        verify(sendMessageUseCase, times(1)).deleteMessage(any())
    }
}
