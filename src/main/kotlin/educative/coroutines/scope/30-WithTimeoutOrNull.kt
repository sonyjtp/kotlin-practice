package org.sony.educative.coroutines.scope

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.milliseconds


class User30(val name: String)

private suspend fun fetchUser(): User30 {
    delay(2000.milliseconds) // too long
    return User30("John Smith") // won't reach this line
}

suspend fun getUserOrNull(): User30? =
    withTimeoutOrNull(1000.milliseconds) {
        fetchUser()
    }

suspend fun main(): Unit = coroutineScope {
    val user = getUserOrNull()
    println("User: $user")
}