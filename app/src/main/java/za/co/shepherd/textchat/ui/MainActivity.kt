package za.co.shepherd.textchat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import za.co.shepherd.textchat.di.AppModule
import za.co.shepherd.textchat.ui.chat.ChatScreen
import za.co.shepherd.textchat.ui.theme.TextChatTheme
import za.co.shepherd.textchat.viewmodel.ChatViewModel

class MainActivity : ComponentActivity() {
    private val repo by lazy { AppModule.provideRepository() }
    private val sendMessageUseCase by lazy { AppModule.provideSendMessageUseCase() }
    private val viewModel by lazy { ChatViewModel(repo, sendMessageUseCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextChatTheme {
                ChatScreen(viewModel = viewModel)
            }
        }
    }
}