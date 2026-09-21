package org.sony.educative.coroutines.testing

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class Mocks {
    @Test
    fun  `should load data concurrently`() = runTest {
        val userRepo = mockk<UserDataRepository>()
        coEvery { userRepo.getName() } coAnswers {
            delay(600.milliseconds)
            "Ben"
        }
        coEvery { userRepo.getFriends() } coAnswers {
            delay(700.milliseconds)
            listOf(Friend("some-friend-id-1"))
        }
        coEvery { userRepo.getProfile() } coAnswers {
            delay(800.milliseconds)
            Profile("Example description")
        }
        val useCase = FetchUserUseCase(userRepo)
        useCase.fetchUserData()
        assertEquals(800, currentTime)
    }
}