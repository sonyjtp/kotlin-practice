package org.sony.educative.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    val job = Job()
    launch (context = job) {
        delay(1000.milliseconds)
        println("Text 1")
    }
    launch (context = job) {
        delay(2000.milliseconds)
        println("Text 2")
    }
    delay(1100.milliseconds)
    job.complete() // or job.completeExceptionally(exception = Exception("error"))
    job.join()
}