package za.co.shepherd.textchat.ui.chat

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import za.co.shepherd.textchat.data.FakeMessageRepository
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.domain.SendMessageUseCase
import za.co.shepherd.textchat.viewmodel.ChatUiState
import za.co.shepherd.textchat.viewmodel.ChatViewModel

class ChatScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun buildViewModel(initialState: ChatUiState = ChatUiState()): ChatViewModel {
        val repo = FakeMessageRepository()
        val useCase = SendMessageUseCase(repo)

        // preload messages safely
        if (initialState.messages.isNotEmpty()) {
            kotlinx.coroutines.runBlocking {
                initialState.messages.forEach { repo.sendMessage(it) }
            }
        }

        return ChatViewModel(repo, useCase)
    }

    @Test
    fun displaysMessages() {
        val mockViewModel = buildViewModel(
            ChatUiState(
                messages = listOf(
                    Message("1", "Hello", true, 123456789),
                    Message("2", "World", false, 123456790)
                )
            )
        )

        composeTestRule.setContent {
            ChatScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Hello").assertIsDisplayed()
        composeTestRule.onNodeWithText("World").assertIsDisplayed()
    }

    @Test
    fun displaysErrorMessage() {
        val mockViewModel = buildViewModel()
        // inject error state manually
        mockViewModel.clearError()
        mockViewModel.send("")

        composeTestRule.setContent {
            ChatScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Message cannot be empty").assertIsDisplayed()
    }

    @Test
    fun sendsMessageOnClick() {
        val mockViewModel = buildViewModel()

        composeTestRule.setContent {
            ChatScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Send").performClick()
    }

    @Test
    fun updatesMessageInput() {
        val mockViewModel = buildViewModel()

        composeTestRule.setContent {
            ChatScreen(viewModel = mockViewModel)
        }

        mockViewModel.onMessageTextChanged("New message")

        composeTestRule.onNodeWithText("New message").assertIsDisplayed()
    }

    @Test
    fun showsSnackbarOnErrorMessage() {
        val viewModel = buildViewModel()
        // Simulate error
        viewModel.send("")

        composeTestRule.setContent {
            ChatScreen(viewModel = viewModel)
        }

        // Assert snackbar with error message is displayed
        composeTestRule.onNodeWithText("Message cannot be empty").assertIsDisplayed()
    }
}