package za.co.shepherd.textchat.ui.components

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import za.co.shepherd.textchat.R
import za.co.shepherd.textchat.ui.theme.TextChatTheme
import za.co.shepherd.textchat.util.TestTags.MESSAGE_INPUT_TEXT_FIELD
import za.co.shepherd.textchat.util.TestTags.SEND_BUTTON

class MessageInputTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun initialStateTextFieldisEmptysendButtonisDisabled() {
        composeTestRule.setContent {
            TextChatTheme {
                MessageInput(
                    message = "",
                    onMessageChange = {},
                    onSendClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(MESSAGE_INPUT_TEXT_FIELD).assertExists()
        composeTestRule.onNodeWithTag(SEND_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun when_typing_textFieldupdatesSendButtonisEnabled() {
        composeTestRule.setContent {
            val message = remember { mutableStateOf("") }
            TextChatTheme {
                MessageInput(
                    message = message.value,
                    onMessageChange = { message.value = it },
                    onSendClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(MESSAGE_INPUT_TEXT_FIELD)
            .performTextInput("Hello")

        composeTestRule.onNodeWithText("Hello").assertExists()
        composeTestRule.onNodeWithTag(SEND_BUTTON).assertIsEnabled()
        composeTestRule.onNodeWithText(getString(R.string.send)).assertIsDisplayed()
    }

    @Test
    fun when_send_clicked_on_send_click_is_called() {
        var sent = false
        composeTestRule.setContent {
            TextChatTheme {
                MessageInput(
                    message = "Hello",
                    onMessageChange = {},
                    onSendClick = { sent = true }
                )
            }
        }

        composeTestRule.onNodeWithTag(SEND_BUTTON).performClick()

        assertTrue(sent)
    }

    @Test
    fun when_input_is_only_whitespaces_sendButton_is_disabled() {
        composeTestRule.setContent {
            val message = remember { mutableStateOf("") }
            TextChatTheme {
                MessageInput(
                    message = message.value,
                    onMessageChange = { message.value = it },
                    onSendClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(MESSAGE_INPUT_TEXT_FIELD)
            .performTextInput("   ")

        composeTestRule.onNodeWithTag(SEND_BUTTON).assertIsNotEnabled()
    }

    @Test
    fun placeholder_is_displayed_when_message_is_empty() {
        composeTestRule.setContent {
            MessageInput(
                message = "",
                onMessageChange = {},
                onSendClick = {}
            )
        }
        composeTestRule.onNodeWithText("Type a message").assertExists()
    }
    @Test
    fun placeholder_is_not_displayed_when_message_is_not_empty() {
        composeTestRule.setContent {
            MessageInput(
                message = "Hello",
                onMessageChange = {},
                onSendClick = {}
            )
        }
        composeTestRule.onNodeWithText("Type a message").assertDoesNotExist()
    }

    private fun getString(@StringRes id: Int): String {
        return ApplicationProvider.getApplicationContext<Context>().getString(id)
    }
}