package za.co.shepherd.textchat.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import za.co.shepherd.textchat.data.model.Message
import za.co.shepherd.textchat.data.repository.MessageRepository
import za.co.shepherd.textchat.util.ResultWrapper

class SendMessageUseCase(
    private val repo: MessageRepository,
    private val autoReplyDelayMillis: Long = 5_000L,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val replyPatterns = listOf(
        Regex("hello", RegexOption.IGNORE_CASE) to "Hello to you too!",
        Regex("how are you", RegexOption.IGNORE_CASE) to "I'm doing great, thanks for asking!"
    )

    suspend fun sendUserMessage(text: String): ResultWrapper<Unit> {
        if (text.isBlank()) {
            return ResultWrapper.Error("Message cannot be empty")
        }

        return when (val result =
            repo.sendMessage(Message(text = text.trim(), isFromUser = true))) {
            is ResultWrapper.Success -> {
                scheduleReply(text.trim())
                ResultWrapper.Success(Unit)
            }

            is ResultWrapper.Error -> result
        }
    }

    suspend fun deleteMessage(message: Message): ResultWrapper<Unit> {
        return when (val result = repo.deleteMessage(message)) {
            is ResultWrapper.Success -> {
                ResultWrapper.Success(Unit)
            }
            is ResultWrapper.Error -> result
        }
    }

    private fun scheduleReply(originalMessage: String) {
        scope.launch {
            try {
                delay(autoReplyDelayMillis) // Configurable delay
                val replyText = generateReplyText(originalMessage)
                val replyMessage = Message(text = replyText, isFromUser = false)
                repo.sendMessage(Message(text = replyMessage.text, isFromUser = false))
            } catch (e: Exception) {
                // In production: log to crash reporting
                println("Failed to send auto-reply: ${e.message}")
            }
        }
    }

    private fun generateReplyText(userMessage: String): String {
        val reply = replyPatterns.find { it.first.containsMatchIn(userMessage) }?.second
        return reply ?: "Got it: \"$userMessage\""
    }
}