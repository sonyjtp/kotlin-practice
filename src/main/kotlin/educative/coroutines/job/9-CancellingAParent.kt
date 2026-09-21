package org.sony.educative.coroutines.job

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    val parentJob = Job()
    val job = Job(parentJob)
    launch(job) {
        delay(1000.milliseconds)
        println("Text 1")
    }
    launch(job) {
        delay(2000.milliseconds)
        println("Text 2")  // not printed
    }
    delay(1100.milliseconds)
    parentJob.cancel()
    job.children.forEach { it.join() }
}

// parentJob > job > launch1, launch 2