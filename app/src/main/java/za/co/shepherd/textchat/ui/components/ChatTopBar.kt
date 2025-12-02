package za.co.shepherd.textchat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import za.co.shepherd.textchat.ui.theme.Dimensions.largePadding
import za.co.shepherd.textchat.ui.theme.Dimensions.largeXPadding
import za.co.shepherd.textchat.ui.theme.Dimensions.mediumPadding

@Composable
fun ChatTopBar(label: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = largePadding),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .height(largeXPadding)
                .padding(horizontal = mediumPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatTopBarPreview() {
    ChatTopBar(label = "Simple Chat")
}