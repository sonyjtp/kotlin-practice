package org.sony.educative.coroutines.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    val job = Job()
    launch (job) {
        try {
            repeat(10) {
                delay(100.milliseconds)
                println("i=$it")
            }
        } catch (e: CancellationException) {
            println(e)
            throw e
        }
    }
    delay(800.milliseconds)
    job.cancelAndJoin()
    println("Canceled successfully")
}