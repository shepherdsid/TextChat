package za.co.shepherd.textchat.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.data.repository.MessageRepository
import za.co.shepherd.textchat.util.ResultWrapper

class FakeMessageRepository(
    initialMessages: List<Message> = emptyList()
) : MessageRepository {
    private val messagesList = initialMessages.toMutableList()
    private val messagesFlow = MutableStateFlow(messagesList.toList())

    override fun messages(): StateFlow<List<Message>> = messagesFlow
    override suspend fun sendMessage(message: Message): ResultWrapper<Unit> {
        messagesList.add(message)
        messagesFlow.value = messagesList.toList()
        return ResultWrapper.Success(Unit)
    }

    override suspend fun deleteMessage(message: Message): ResultWrapper<Unit> {
        messagesList.remove(message)
        messagesFlow.value = messagesList.toList()
        return ResultWrapper.Success(Unit)
    }

    override suspend fun clear(): ResultWrapper<Unit> {
        messagesList.clear()
        messagesFlow.value = emptyList()
        return ResultWrapper.Success(Unit)
    }
}