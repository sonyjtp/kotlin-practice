package org.sony.educative.coroutines.state

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration.Companion.milliseconds


private val mutex = Mutex()

suspend fun main(): Unit = coroutineScope {
    repeat (5) {
        launch {
            delayAndPrint(it)
        }
    }
}

private suspend fun delayAndPrint(num: Int) {
    mutex.withLock {
        delay(1000.milliseconds)
        println("$num done!")
    }
}