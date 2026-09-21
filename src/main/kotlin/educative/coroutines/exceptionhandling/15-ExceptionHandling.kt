package org.sony.educative.coroutines.exceptionhandling

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds





fun main(): Unit = runBlocking { //0
    launch { // 1
        launch { // 2
            delay(1000.milliseconds)
            throw Error("Some error") // throws after 1s. exception not handled; propagated to parent (1).
        }
        launch { // 3
            delay(2000.milliseconds) // slower than the exception; canceled because parent (1) is canceled.
            println("Will not be printed")
        }
        launch { // 4
            delay(500.milliseconds) // faster than the exception; not canceled even though parent (1) is.
            println("Will be printed")
        }
    } // exception thrown by (2) not handled; propagated to parent (0).
    launch { // 5
        delay(2000.milliseconds)  // slower than the exception; canceled because parent (0) is canceled.
        println("Will not be printed")
    }
}