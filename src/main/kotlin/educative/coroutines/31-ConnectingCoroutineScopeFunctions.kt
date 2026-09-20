package org.sony.educative.coroutines

import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds

suspend fun calculateAnswerOrNull(): Int? =
    withContext(Dispatchers.Default) {
        withTimeoutOrNull(1000.milliseconds) {
            calculateAnswer()
        }
    }
suspend fun calculateAnswer(): Int = coroutineScope {
    delay(500.milliseconds)
    42
}

fun main(): Unit = runBlocking {
    println("The answer is ${calculateAnswerOrNull()}")
}

