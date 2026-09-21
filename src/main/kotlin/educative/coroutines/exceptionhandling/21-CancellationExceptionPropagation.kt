package org.sony.educative.coroutines.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds


data class NonPropagationException(override val message: String): CancellationException(message)

suspend fun main(): Unit = coroutineScope { // not cancelled because of the child's CancellationException
    launch { // cancelled
        launch { // cancelled
            delay(2000.milliseconds)
            println("checkpoint 1") // will not be printed because the parent is cancelled.
        }
        throw NonPropagationException("Some Error!")
    }
    launch { // not cancelled because the sibling threw a CancellationException which won't propagate.
        delay(2000.milliseconds)
        println("checkpoint 2")
    }
}