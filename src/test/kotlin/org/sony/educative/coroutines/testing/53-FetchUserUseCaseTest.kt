package org.sony.educative.coroutines.testing

import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.coroutines.ContinuationInterceptor

class `53-FetchUserUseCaseTest` {
    val userRepo = mockk<UserDataRepository>()


    fun testFetchUserUseCase() = runTest {
        val testDispatcher = this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        val useCase = `53-FetchUserUseCase`(
            userRepo = userRepo,
            ioDispatcher = testDispatcher)
        val result = useCase.fetchUserData()
    }


}