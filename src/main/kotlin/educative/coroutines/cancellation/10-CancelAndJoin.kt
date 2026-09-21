package org.sony.educative.coroutines.cancellation

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    val job = launch {
        repeat(1_000) { i ->
            delay(200.milliseconds)
            println("Printing $i")
        }
    }
    delay(1100.milliseconds)
    job.cancelAndJoin()
    println("Canceled successfully")
}