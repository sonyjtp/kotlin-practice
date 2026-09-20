package org.sony.educative.coroutines

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.milliseconds

// https://www.educative.io/courses/mastering-kotlin-coroutines/challenge-coroutine-scope-functions
fun main(): Unit = runBlocking {
    try {
        test()
    } catch (_: TimeoutCancellationException) {
        println("Cancelled")
    }

}

suspend fun test() = withTimeout(1400.milliseconds) {
    delay(1000.milliseconds)
    println("Educative")
    delay(1000.milliseconds)
    println("Inc")
}
