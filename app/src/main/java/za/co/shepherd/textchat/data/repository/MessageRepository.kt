package za.co.shepherd.textchat.data.repository

import kotlinx.coroutines.flow.Flow
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.util.ResultWrapper

interface MessageRepository {
    fun messages(): Flow<List<Message>>
    suspend fun sendMessage(message: Message): ResultWrapper<Unit>
    suspend fun deleteMessage(message: Message): ResultWrapper<Unit>
    suspend fun clear(): ResultWrapper<Unit>
}