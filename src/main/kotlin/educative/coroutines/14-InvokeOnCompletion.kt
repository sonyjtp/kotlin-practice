package org.sony.educative.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {
    val job = Job()
    launch(job) {
        println("${System.currentTimeMillis()}: Execution started")
        delay(2000.milliseconds)
        println("${System.currentTimeMillis()}: Execution completed") // won't be printed
    }
    delay(100.milliseconds)
    println("${System.currentTimeMillis()}: 1. Job completed? ${job.isCompleted}, or cancelled? ${job.isCancelled}")
    // registers a Callback synchronously, and returns DisposableHandle. Invoked only when the job's internal
    // state machine decides the job has reached a terminal state
    job.invokeOnCompletion {
        println("${System.currentTimeMillis()}: 2. Job completed? ${job.isCompleted}, or cancelled? ${job.isCancelled}")
        println("Close resources")
    }
    delay(500.milliseconds)
    job.cancelAndJoin()
    println("Done")
}