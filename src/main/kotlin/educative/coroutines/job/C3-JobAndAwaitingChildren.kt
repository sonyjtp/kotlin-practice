package org.sony.educative.coroutines.job

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds


 fun main(): Unit = runBlocking {
    val parentJob = Job()
    launch(parentJob) {
        delay(1000.milliseconds)
        println("Educative")
    }
    launch(parentJob) {
        delay(2000.milliseconds)
        println("Inc.")
    }
    parentJob.complete()
    parentJob.join()
    println("Completed")
}