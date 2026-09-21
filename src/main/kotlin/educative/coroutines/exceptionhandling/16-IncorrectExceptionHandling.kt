package org.sony.educative.coroutines.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    try {
        launch {
            delay(1000.milliseconds)
            throw Error("Error!")
        }
    } catch (e: Exception) {
        // will not be printed. launch is fire-and-forget. It doesn't wait for the thrown error.
        // try-catch is useless here.
        println("Error!: ${e.message}")
    }
    launch {
        delay(2000.milliseconds)
        println("Launch 2!") // will not be printed. Program completes before this line is executed
    }
}