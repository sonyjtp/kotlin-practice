package org.sony.educative.coroutines

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {
    // Don't do this, SupervisorJob with one child
    // and no parent works similar to just Job
    launch(context = SupervisorJob()) { // 1
        launch {
            delay(1000.milliseconds)
            throw Error("Some error")
        }

        launch {
            delay(2000.milliseconds)
            println("Will not be printed")
        }
    }
    delay(3000.milliseconds)
}