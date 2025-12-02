package za.co.shepherd.textchat.di

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import za.co.shepherd.textchat.data.repository.InMemoryMessageRepository
import za.co.shepherd.textchat.util.ResultWrapper

class AppModuleTest {

    @Before
    fun resetRepo() = runTest {
        // Ensure isolation between tests
        AppModule.provideRepository().clear()
    }

    @Test
    fun `repository is singleton and in memory`() {
        val r1 = AppModule.provideRepository()
        val r2 = AppModule.provideRepository()

        assertSame(r1, r2)
        assertTrue(r1 is InMemoryMessageRepository)
    }

    @Test
    fun `provideRepository returns InMemoryMessageRepository`() {
        val repo = AppModule.provideRepository()
        assertTrue(repo is InMemoryMessageRepository)
    }

    @Test
    fun `usecase uses singleton repo and records user message`() = runTest {
        val repo = AppModule.provideRepository()
        val useCase = AppModule.provideSendMessageUseCase()

        val result = useCase.sendUserMessage("Hello")

        assertTrue(result is ResultWrapper.Success)
        val messages = repo.messages().first()
        assertEquals(1, messages.size)
        assertTrue(messages[0].isFromUser)
        assertEquals("Hello", messages[0].text)
    }
}