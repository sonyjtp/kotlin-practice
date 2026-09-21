package org.sony.educative.coroutines.state

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


// https://www.educative.io/courses/mastering-kotlin-coroutines/challenge-the-problem-with-shared-state

private val mutex = Mutex()

suspend fun main(): Unit = coroutineScope {
    mutex.withLock {
        println("Educative")
        mutex.withLock {
            println("Inc.")
        }
    }
}

