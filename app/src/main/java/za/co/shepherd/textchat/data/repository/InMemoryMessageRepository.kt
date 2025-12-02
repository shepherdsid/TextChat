package za.co.shepherd.textchat.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.util.ResultWrapper

class InMemoryMessageRepository : MessageRepository {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    override fun messages(): StateFlow<List<Message>> = _messages.asStateFlow()

    override suspend fun sendMessage(message: Message): ResultWrapper<Unit> {
        return try {
            _messages.value += message
            println("sendMessage called with text: ${message.text} and added to repository")
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Error("Failed to add message", e)
        }
    }

    override suspend fun deleteMessage(message: Message): ResultWrapper<Unit> {
        return try {
            _messages.value -= message
            println("deleteMessage called with text: ${message.text} and removed from repository")
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Error("Failed to delete message", e)
        }
    }

    override suspend fun clear(): ResultWrapper<Unit> {
        return try {
            _messages.value = emptyList()
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Error("Failed to clear messages", e)
        }
    }
}