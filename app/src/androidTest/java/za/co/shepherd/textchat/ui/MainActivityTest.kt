package za.co.shepherd.textchat.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainActivity_launchesSuccessfully() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.use {
            assert(it.state.isAtLeast(androidx.lifecycle.Lifecycle.State.RESUMED))
        }
    }
    @Test
    fun chatScreen_isDisplayed() {
        composeTestRule.onNodeWithTag("ChatScreenRoot").assertIsDisplayed()
    }
}