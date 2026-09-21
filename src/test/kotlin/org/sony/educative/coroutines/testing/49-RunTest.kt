package org.sony.educative.coroutines.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class RunTestExample {
    @Test
    fun `Should produce user sequentially`() = runTest {
        val userDataRepository = FakeDelayedUserDataRepository()
        val useCase = ProduceUserUseCase(userDataRepository)
        useCase.runSequential()
        assertEquals(2000, currentTime)
    }
    @Test
    fun `Should produce user simultaneously`() = runTest {
        val userDataRepository = FakeDelayedUserDataRepository()
        val useCase = ProduceUserUseCase(userDataRepository)
        useCase.runSimultaneous()
        assertEquals(1000, currentTime)
    }
}

class ProduceUserUseCase(private val userDataRepository: FakeDelayedUserDataRepository) {
    suspend fun runSequential() {
        coroutineScope {  userDataRepository.getProfile() }
        coroutineScope { userDataRepository.getName() }
    }
    suspend fun runSimultaneous() = coroutineScope {
        launch {  userDataRepository.getProfile() }
        launch { userDataRepository.getName() }
    }
}