package za.co.shepherd.textchat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.data.repository.MessageRepository
import za.co.shepherd.textchat.domain.SendMessageUseCase
import za.co.shepherd.textchat.util.ResultWrapper

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val errorMessage: String? = null
)

class ChatViewModel(
    private val repo: MessageRepository,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState

    private val _error = MutableStateFlow<String?>(null)
    private val _currentMessageText = MutableStateFlow("")
    val currentMessageText: StateFlow<String> = _currentMessageText.asStateFlow()

    init {
        viewModelScope.launch {
            repo.messages().collect { messages ->
                _uiState.value = _uiState.value.copy(messages = messages)
            }
        }

        viewModelScope.launch {
            _error.collect { error ->
                _uiState.value = _uiState.value.copy(errorMessage = error)
            }
        }
    }

    fun onMessageTextChanged(newText: String) {
        _currentMessageText.value = newText
    }

    fun send(text: String) {
        viewModelScope.launch {
            when (val result = sendMessageUseCase.sendUserMessage(text)) {
                is ResultWrapper.Success -> {
                    _error.value = null
                    _currentMessageText.value = ""
                }
                is ResultWrapper.Error -> {
                    _error.value = result.message
                }
                else -> {
                    // Handle unexpected cases
                    _error.value = "Unexpected error occurred"
                }
            }
        }
    }

    fun deleteMessage(message: Message) {
        viewModelScope.launch {
            when (val result = sendMessageUseCase.deleteMessage(message)) {
                is ResultWrapper.Success -> {
                    _currentMessageText.value = ""
                }
                is ResultWrapper.Error -> {
                    _error.value = result.message
                }
                else -> {
                    // Handle unexpected cases
                    _error.value = "Unexpected error occurred"
                }
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}