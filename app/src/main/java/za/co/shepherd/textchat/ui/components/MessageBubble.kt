package za.co.shepherd.textchat.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.ui.theme.Dimensions.maxWidth
import za.co.shepherd.textchat.ui.theme.Dimensions.mediumPadding
import za.co.shepherd.textchat.ui.theme.Dimensions.smallMediumPadding
import za.co.shepherd.textchat.ui.theme.Dimensions.smallPadding
import za.co.shepherd.textchat.ui.theme.Dimensions.smallerPadding
import za.co.shepherd.textchat.util.TestTags.REPLY_MESSAGE
import za.co.shepherd.textchat.util.TestTags.USER_MESSAGE

@Composable
fun MessageBubble(message: Message, onClick: () -> Unit) {
    val horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
    val backgroundColor = if (message.isFromUser)
        MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    val textColor = if (message.isFromUser)
        MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
    val bubbleShape = if (message.isFromUser)
        RoundedCornerShape(
            topStart = mediumPadding,
            topEnd = smallerPadding,
            bottomStart = mediumPadding,
            bottomEnd = mediumPadding
        )
    else
        RoundedCornerShape(
            topStart = smallerPadding,
            topEnd = mediumPadding,
            bottomStart = mediumPadding,
            bottomEnd = mediumPadding
        )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = horizontalAlignment)
    ) {
        Card(
            shape = bubbleShape,
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            modifier = Modifier
                .padding(vertical = smallerPadding)
                .testTag(if (message.isFromUser) USER_MESSAGE else REPLY_MESSAGE)
                .widthIn(max = maxWidth)
                .clickable { onClick() }
        ) {
            Text(
                text = message.text,
                color = textColor,
                modifier = Modifier.padding(
                    horizontal = smallMediumPadding,
                    vertical = smallPadding
                ),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageBubblePreview() {
    MessageBubble(
        message = Message(
            id = "1",
            text = "Hello, this is a sample message!",
            isFromUser = true,
            timestamp = System.currentTimeMillis()
        ),
        onClick = {}
    )
}