package org.sony.educative.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main() = runBlocking {
    val a = launch {
        delay(200.milliseconds)
        println("checkpoint 1")
        delay(1000.milliseconds)
        println("checkpoint 3") // printed 5th
    }
    println("a is calculated") // printed 1st
    val b = coroutineScope {
        println("checkpoint 2")  // printed 2nd
        delay(100.milliseconds)
        20
    }
    println(a) // StandaloneCoroutine{Active}@.... // printed 3rd; 3rd in sequence
    println(b) // 20 // printed 4th.
}