package org.sony.educative.coroutines.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds


suspend fun main(): Unit = withContext(Dispatchers.IO.limitedParallelism(150)) {
    println("Number of available processors: ${Runtime.getRuntime().availableProcessors()}")
    repeat(200) {
        launch {
            delay(100L.milliseconds)
            println("1 - Launching ${Thread.currentThread().name}")
        }
        launch {
            delay(100L.milliseconds)
            println("2 - Launching ${Thread.currentThread().name}")
        }
        launch {
            delay(100L.milliseconds)
            println("3 - Launching ${Thread.currentThread().name}")
        }
        launch {
            delay(100L.milliseconds)
            println("4 - Launching ${Thread.currentThread().name}")
        }
        launch {
            delay(100L.milliseconds)
            println("5 - Launching ${Thread.currentThread().name}")
        }
    }
}