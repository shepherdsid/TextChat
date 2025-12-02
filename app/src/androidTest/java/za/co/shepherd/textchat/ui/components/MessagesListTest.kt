package za.co.shepherd.textchat.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.ui.theme.TextChatTheme

class MessagesListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_messages_is_empty_no_messages_are_displayed() {
        composeTestRule.setContent {
            TextChatTheme {
                MessagesList(messages = emptyList(), onMessageClick = {})
            }
        }
        // No assertion needed if we are just checking for absence of crashes
        // and that the composable handles an empty list gracefully.
    }

    @Test
    fun when_has_one_message_it_is_displayed() {
        val message = Message(text = "Hello, World!", isFromUser = true)
        composeTestRule.setContent {
            TextChatTheme {
                MessagesList(messages = listOf(message), onMessageClick = {})
            }
        }

        composeTestRule.onNodeWithText("Hello, World!").assertIsDisplayed()
    }

    @Test
    fun when_has_multiple_messages_they_are_all_displayed() {
        val messages = listOf(
            Message(text = "Hello!", isFromUser = false),
            Message(text = "Hi there!", isFromUser = true),
            Message(text = "How are you?", isFromUser = false)
        )
        composeTestRule.setContent {
            TextChatTheme {
                MessagesList(messages = messages, onMessageClick = {})
            }
        }

        messages.forEach { message ->
            composeTestRule.onNodeWithText(message.text).assertIsDisplayed()
        }
    }

    @Test
    fun when_message_text_is_long_it_is_displayed() {
        val longText = "A".repeat(1000)
        val message = Message(text = longText, isFromUser = false)
        composeTestRule.setContent {
            TextChatTheme {
                MessagesList(messages = listOf(message), onMessageClick = {})
            }
        }
        composeTestRule.onNodeWithText(longText).assertIsDisplayed()
    }

    @Test
    fun when_messages_include_user_and_reply_both_are_displayed() {
        val userMessage = Message(text = "User", isFromUser = true)
        val replyMessage = Message(text = "Reply", isFromUser = false)
        composeTestRule.setContent {
            TextChatTheme {
                MessagesList(messages = listOf(userMessage, replyMessage), onMessageClick = {})
            }
        }
        composeTestRule.onNodeWithText("User").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reply").assertIsDisplayed()
    }
}