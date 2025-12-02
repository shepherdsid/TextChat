package za.co.shepherd.textchat.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import za.co.shepherd.textchat.R
import za.co.shepherd.textchat.ui.components.ChatTopBar
import za.co.shepherd.textchat.ui.components.MessageInput
import za.co.shepherd.textchat.ui.components.MessagesList
import za.co.shepherd.textchat.viewmodel.ChatViewModel

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val state by viewModel.uiState.collectAsState()
    val currentMessageText by viewModel.currentMessageText.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.testTag("ChatScreenRoot")) {

        Scaffold(
            topBar = {
                ChatTopBar(label = stringResource(R.string.simple_chat_title))
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                MessagesList(
                    messages = state.messages,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    onMessageClick = { message -> viewModel.deleteMessage(message) }
                )

                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                MessageInput(
                    message = currentMessageText,
                    onMessageChange = { viewModel.onMessageTextChanged(it) },
                    onSendClick = { viewModel.send(currentMessageText) }
                )
            }
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { errorMessage ->
            snackbarHostState.showSnackbar(errorMessage)
            viewModel.clearError()
        }
    }
}