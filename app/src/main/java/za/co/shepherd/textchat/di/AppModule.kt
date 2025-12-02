package za.co.shepherd.textchat.di

import za.co.shepherd.textchat.data.repository.InMemoryMessageRepository
import za.co.shepherd.textchat.data.repository.MessageRepository
import za.co.shepherd.textchat.domain.SendMessageUseCase

object AppModule {
    private val repo: MessageRepository by lazy { InMemoryMessageRepository() }

    fun provideRepository(): MessageRepository = repo

    fun provideSendMessageUseCase() = SendMessageUseCase(repo)
}