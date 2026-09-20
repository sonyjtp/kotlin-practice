package org.sony.educative.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import org.sony.User
import kotlin.time.Duration.Companion.milliseconds

private suspend fun fetchUser(): User {
    delay(2000.milliseconds) // too long
    return User(1, "John Smith", 30) // won't reach this line
}

suspend fun getUserOrNull(): User? =
    withTimeoutOrNull(1000.milliseconds) {
        fetchUser()
    }

suspend fun main(): Unit = coroutineScope {
    val user = getUserOrNull()
    println("User: $user")
}