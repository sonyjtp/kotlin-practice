package org.sony.educative.coroutines.scope

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds


suspend fun runTask() = coroutineScope {
    launch {
        delay(1000.milliseconds)
        println("${coroutineContext[CoroutineName]?.name}: Finished task 1")
    }
    launch {
        delay(2000.milliseconds)
        println("${coroutineContext[CoroutineName]?.name}: Finished task 2")
    }
}

fun main() = runBlocking (CoroutineName("parent")) {
    println("Before")
    runTask()
    println("After")
}

