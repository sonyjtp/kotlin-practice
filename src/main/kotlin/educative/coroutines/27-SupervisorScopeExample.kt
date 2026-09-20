package org.sony.educative.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration.Companion.milliseconds

fun main() = runBlocking {
    println("Before")

    supervisorScope {
        launch {
            delay(1000.milliseconds)
            throw Error()
        }
        launch {
            // slower but doesn't fail even if its sibling fails due to the parent being in supervisorScope
            delay(2000.milliseconds)
            println("Done")
        }
    }
    println("After")
}