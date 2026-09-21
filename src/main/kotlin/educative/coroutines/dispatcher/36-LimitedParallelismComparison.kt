package org.sony.educative.coroutines.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {
    launch {
        printTimeTaken(Dispatchers.IO)
    }
    launch {
        printTimeTaken(Dispatchers.IO.limitedParallelism(100))
    }
}
suspend fun printTimeTaken(dispatcher: CoroutineDispatcher) {

    val timeTaken = measureTimeMillis {
        coroutineScope {
            repeat(100) {
                launch (dispatcher) {
                    Thread.sleep(1000)
                }
            }
        }
    }
    println("Time taken by ${dispatcher}: $timeTaken ms")
}