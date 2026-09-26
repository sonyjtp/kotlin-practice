package org.sony.educative.coroutines.testing

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


// https://www.educative.io/courses/mastering-kotlin-coroutines/testing-functions-properties#Testing-functions-that-launch-new-coroutines
data class Notification(
    val id: Int,
    val title: String,
) {
    var isSent: Boolean = false
}

interface NotificationRepository {
    suspend fun getNotificationsToSend(): List<Notification>
    suspend fun markAsSent(notification: Notification)
}

interface NotificationService {
    fun send(notification: Notification): Boolean
}

class NotificationSender(
    //This is the class that will be tested
    private val service: NotificationService,
    private val repository: NotificationRepository,
) {
    suspend fun sendNotifications() = coroutineScope {
        val notifications = repository.getNotificationsToSend() // takes 10 ms
        for (notification in notifications) {
            launch { // 100 coroutines launched
                service.send(notification)
                repository.markAsSent(notification) // takes 10 ms for all 100
            }
        }
    }
}
