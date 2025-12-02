package za.co.shepherd.textchat.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import za.co.shepherd.textchat.ui.theme.TextChatTheme

class ChatTopBarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsTitleCorrectly() {
        val testLabel = "Chat Title"
        composeTestRule.setContent {
            ChatTopBar(
                label = testLabel
            )
        }
        composeTestRule
            .onNodeWithText(testLabel)
            .assertIsDisplayed()
    }

    @Test
    fun doesNotDisplaysWithEmptyLabel() {
        val testLabel = ""
        composeTestRule.setContent {
            TextChatTheme {
                ChatTopBar(label = testLabel)
            }
        }

        composeTestRule
            .onNodeWithText(testLabel)
            .assertIsNotDisplayed()
    }

    @Test
    fun showsLongTitleCorrectly() {
        val longLabel = "This is a very long chat title to test layout handling in ChatTopBar"
        composeTestRule.setContent {
            TextChatTheme {
                ChatTopBar(label = longLabel)
            }
        }
        composeTestRule.onNodeWithText(longLabel).assertIsDisplayed()
    }
}