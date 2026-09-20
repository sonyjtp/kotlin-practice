package org.sony.educative.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit  = runBlocking{
    supervisorScope {
        launch {
            delay(1000.milliseconds)
            throw Error("Some Error!")
        }

        launch {
            delay(2000.milliseconds)
            println("Some Success!")
        }
    }

    delay(1000.milliseconds)
    println("Done!")
}