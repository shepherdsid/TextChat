package za.co.shepherd.textchat.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.ui.theme.Dimensions.smallPadding

@Composable
fun MessagesList(messages: List<Message>, modifier: Modifier = Modifier, onMessageClick: (Message) -> Unit) {
    LazyColumn(
        modifier = modifier.padding(all = smallPadding),
        reverseLayout = false
    ) {
        items(messages) { message ->
            MessageBubble(message = message, onClick = { onMessageClick(message) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesListPreview() {
    val sampleMessages = listOf(
        Message(id = "1", text = "Hello!", isFromUser = true, timestamp = System.currentTimeMillis()),
        Message(id = "2", text = "Hi there!", isFromUser = false, timestamp = System.currentTimeMillis())
    )
    MessagesList(messages = sampleMessages, onMessageClick = {})
}