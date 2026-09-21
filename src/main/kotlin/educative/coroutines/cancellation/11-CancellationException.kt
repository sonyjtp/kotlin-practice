package org.sony.educative.coroutines.cancellation

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    val job = launch {
        try {
            repeat(1_000) { i ->
                delay(200.milliseconds)
                println("Printing $i")
            }
        } catch (e: CancellationException) {
            println(e.message)
            throw e
        }
    }
    delay(1100.milliseconds)
    job.cancelAndJoin()
    println("Canceled successfully")
}