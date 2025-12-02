package za.co.shepherd.textchat.util

import org.junit.Assert.assertEquals
import org.junit.Test

class TestTagsTest {
    @Test
    fun `test tags are defined correctly`() {
        assertEquals("SendButton", TestTags.SEND_BUTTON)
        assertEquals("MessageInputTextField", TestTags.MESSAGE_INPUT_TEXT_FIELD)
        assertEquals("UserMessage", TestTags.USER_MESSAGE)
        assertEquals("ReplyMessage", TestTags.REPLY_MESSAGE)
    }
}