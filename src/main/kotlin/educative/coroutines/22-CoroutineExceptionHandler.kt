package org.sony.educative.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {
    val handler =
        CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
    val scope = CoroutineScope(SupervisorJob() + handler)
    scope.launch { // 1
        delay(1000.milliseconds)
        throw Error("Some error")
    }
    scope.launch { //2
        delay(2000.milliseconds)
        println("Will be printed")
    }
    delay(3000.milliseconds)
}