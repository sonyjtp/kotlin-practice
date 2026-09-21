package org.sony.educative.coroutines.cancellation

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {
    val job = launch {
        try {
            delay(1000.milliseconds)
            println("Executing")
        } finally {
            withContext(NonCancellable) {
                delay(1000.milliseconds)
                println("Closing resources")
            }
        }
    }
    delay(500.milliseconds)
    job.cancelAndJoin()
    println("Cancelled")
}