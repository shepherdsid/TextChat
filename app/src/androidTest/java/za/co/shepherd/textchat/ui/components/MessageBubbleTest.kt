package za.co.shepherd.textchat.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.ui.theme.TextChatTheme
import za.co.shepherd.textchat.util.TestTags.REPLY_MESSAGE
import za.co.shepherd.textchat.util.TestTags.USER_MESSAGE

class MessageBubbleTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun userMessageDisplaysTextandHasUserTag() {
        val text = "Hello User!"
        val message = Message(text = text, isFromUser = true)

        composeTestRule.setContent {
            TextChatTheme {
                MessageBubble(message = message, onClick = {})
            }
        }

        composeTestRule.onNodeWithText(text).assertIsDisplayed()
        composeTestRule.onNodeWithTag(USER_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun replyMessage_displaysText_andHasReplyTag() {
        val text = "Hello Reply!"
        val message = Message(text = text, isFromUser = false)

        composeTestRule.setContent {
            TextChatTheme {
                MessageBubble(message = message, onClick = {})
            }
        }

        composeTestRule.onNodeWithText(text).assertIsDisplayed()
        composeTestRule.onNodeWithTag(REPLY_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun user_isAlignedToEnd_and_reply_isAlignedToStart() {
        val reply = Message(text = "Left aligned (reply)", isFromUser = false)
        val user = Message(text = "Right aligned (user)", isFromUser = true)

        composeTestRule.setContent {
            TextChatTheme {
                Column(Modifier.fillMaxWidth()) {
                    MessageBubble(message = reply, onClick = {})
                    MessageBubble(message = user, onClick = {})
                }
            }
        }

        // Compare left edges: user bubble should be further right than reply bubble.
        val replyLeft = composeTestRule
            .onNodeWithTag(REPLY_MESSAGE)
            .fetchSemanticsNode().boundsInRoot.left
        val userLeft = composeTestRule
            .onNodeWithTag(USER_MESSAGE)
            .fetchSemanticsNode().boundsInRoot.left

        assertTrue("User bubble should be to the right of reply bubble", userLeft > replyLeft)
    }

    @Test
    fun userMessage_displaysText_andHasUserTag_andIsAlignedRight() {
        val message = Message(text = "User", isFromUser = true)
        composeTestRule.setContent { MessageBubble(message = message, onClick = {}) }
        composeTestRule.onNodeWithText("User").assertIsDisplayed()
        composeTestRule.onNodeWithTag(USER_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun replyMessage_displaysText_andHasReplyTag_andIsAlignedLeft() {
        val message = Message(text = "Reply", isFromUser = false)
        composeTestRule.setContent { MessageBubble(message = message, onClick = {}) }
        composeTestRule.onNodeWithText("Reply").assertIsDisplayed()
        composeTestRule.onNodeWithTag(REPLY_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun emptyUserMessage_displaysNothing_butHasUserTag() {
        val message = Message(text = "", isFromUser = true)
        composeTestRule.setContent { MessageBubble(message = message, onClick = {}) }
        composeTestRule.onNodeWithTag(USER_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun emptyReplyMessage_displaysNothing_butHasReplyTag() {
        val message = Message(text = "", isFromUser = false)
        composeTestRule.setContent { MessageBubble(message = message, onClick = {}) }
        composeTestRule.onNodeWithTag(REPLY_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun longTextMessage_displaysFullText() {
        val longText = "A".repeat(1000)
        val message = Message(text = longText, isFromUser = true)
        composeTestRule.setContent { MessageBubble(message = message, onClick = {}) }
        composeTestRule.onNodeWithText(longText).assertIsDisplayed()
    }

    @Test
    fun user_and_reply_areCorrectlyAligned() {
        val reply = Message(text = "Reply", isFromUser = false)
        val user = Message(text = "User", isFromUser = true)
        composeTestRule.setContent {
            Column(Modifier.fillMaxWidth()) {
                MessageBubble(message = reply, onClick = {})
                MessageBubble(message = user, onClick = {})
            }
        }
        val replyLeft = composeTestRule.onNodeWithTag(REPLY_MESSAGE).fetchSemanticsNode().boundsInRoot.left
        val userLeft = composeTestRule.onNodeWithTag(USER_MESSAGE).fetchSemanticsNode().boundsInRoot.left
        assertTrue(userLeft > replyLeft)
    }
}