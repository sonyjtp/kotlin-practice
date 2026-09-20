package org.sony.educative.coroutines

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.milliseconds

suspend fun test1(): Int = withTimeout(1500.milliseconds) {
    delay(1000.milliseconds)
    println("Still thinking")
    delay(1000.milliseconds)
    println("Done!") // won't be printed. Times out before that.
    42
}

suspend fun main(): Unit = coroutineScope {
    try {
        test1()
    } catch (e: TimeoutCancellationException) {
        println("Cancelled: ${e.message}")
    }
    delay(1000.milliseconds) // Extra timeout does not help,
    // `test` body was cancelled
}