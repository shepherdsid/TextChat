package za.co.shepherd.textchat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import za.co.shepherd.textchat.R
import za.co.shepherd.textchat.ui.theme.Dimensions.largePadding
import za.co.shepherd.textchat.ui.theme.Dimensions.largeXPadding
import za.co.shepherd.textchat.ui.theme.Dimensions.smallPadding
import za.co.shepherd.textchat.util.TestTags.MESSAGE_INPUT_TEXT_FIELD
import za.co.shepherd.textchat.util.TestTags.SEND_BUTTON

@Composable
fun MessageInput(
    message: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = smallPadding
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = smallPadding, vertical = smallPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = onMessageChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = smallPadding)
                    .testTag(MESSAGE_INPUT_TEXT_FIELD),
                placeholder = { Text(stringResource(R.string.type_a_message)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(largePadding),
                maxLines = 5
            )
            Button(
                onClick = onSendClick,
                enabled = message.isNotBlank(),
                modifier = Modifier
                    .height(largeXPadding)
                    .testTag(SEND_BUTTON),
                shape = RoundedCornerShape(largePadding),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            ) {
                Text(
                    text = stringResource(R.string.send),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageInputPreview() {
    MessageInput(
        message = "Sample message",
        onMessageChange = {},
        onSendClick = {}
    )
}