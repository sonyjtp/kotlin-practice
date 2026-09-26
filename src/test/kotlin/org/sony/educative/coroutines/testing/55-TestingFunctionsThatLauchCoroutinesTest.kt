package org.sony.educative.coroutines.testing

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds


class FakeNotificationRepository: NotificationRepository {
    override suspend fun getNotificationsToSend(): List<Notification> = coroutineScope {
        (1..100).map { id ->
            async {
                delay(10.milliseconds)
                Notification(id, "Hello $id!")
            }
        }.awaitAll() // Time taken: 10 ms
    }

    override suspend fun markAsSent(notification: Notification) {
        delay(10.milliseconds)
        notification.isSent = true
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestingFunctionsThatLauchCoroutinesTest {

    private val service: NotificationService = mockk()
    private val repository: NotificationRepository = FakeNotificationRepository()

    private val notificationSender = NotificationSender(service, repository)

    @Test
    fun testSendNotifications() {
        coEvery {
            service.send(any<Notification>())
        } returns true
        runTest {
            notificationSender.sendNotifications()
            advanceUntilIdle()
            // 10 ms for getNotificationsToSend + 10 ms for markAsSent
            assertEquals(20, currentTime)

        }
    }
}
