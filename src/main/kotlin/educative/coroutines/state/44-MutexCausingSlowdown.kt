package org.sony.educative.coroutines.state

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds


private val mutex = Mutex()

suspend fun main() {
    val timeTaken = measureTimeMillis {
        coroutineScope {
            launch {
                repeat(5) {
                    launch {
                        delayAndPrint(it)
                    }
                }
            }
        }
    }
    println("Time taken: $timeTaken ms") // takes more than 500 ms because the other launches are waiting for the mutex
}

private suspend fun delayAndPrint(num: Int) {
    mutex.withLock {
        println("$num done!")
        delay(100.milliseconds)
    }
}