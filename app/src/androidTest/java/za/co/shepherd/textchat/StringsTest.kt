package za.co.shepherd.textchat

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test

class StringsTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun verifyAppNameString() {
        val appName = context.getString(R.string.app_name)
        Assert.assertEquals("TextChat", appName)
    }

    @Test
    fun verifySimpleChatTitleString() {
        val title = context.getString(R.string.simple_chat_title)
        Assert.assertEquals("Simple Chat", title)
    }

    @Test
    fun verifyTypeAMessageString() {
        val typeMessage = context.getString(R.string.type_a_message)
        Assert.assertEquals("Type a message", typeMessage)
    }

    @Test
    fun verifySendString() {
        val send = context.getString(R.string.send)
        Assert.assertEquals("Send", send)
    }
}