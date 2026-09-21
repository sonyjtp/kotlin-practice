package org.sony.educative.coroutines.job

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {
    launch { runJob1() }
    launch { runJob2() }
}

suspend fun runJob1() = coroutineScope {
    val job = Job()
    launch(job) { // the new job replaces one from parent
        delay(1000.milliseconds)
        println("Text 1")
    }
    launch(job) { // the new job replaces one from parent
        delay(2000.milliseconds)
        println("Text 2")
    }
    job.join() // Here we will await forever
    println("Will not be printed")
}

suspend fun runJob2() = coroutineScope {
    val job = Job()
    launch(job) { // the new job replaces one from parent
        delay(1000.milliseconds)
        println("Text 3")
    }
    launch(job) { // the new job replaces one from parent
        delay(2000.milliseconds)
        println("Text 4")
    }
    job.children.forEach { it.join() }
    println("Will be printed")
}