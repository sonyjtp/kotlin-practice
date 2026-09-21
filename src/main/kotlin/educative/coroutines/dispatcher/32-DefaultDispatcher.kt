package org.sony.educative.coroutines.dispatcher

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

// runBlocking sets its own dispatcher if no other is set; so, inside it, the Dispatcher.Default is not the one chosen
// automatically. When we use runBlocking instead of coroutineScope, without mentioning the Dispatcher name for launch,
// all coroutines would be running on the main thread.
suspend fun main() = coroutineScope {
    // to get the number of available processors
    println("availableProcessors = ${Runtime.getRuntime().availableProcessors()}")
    repeat(100) {
        launch { // or launch(Dispatchers.Default) {
            // To make it busy
            List(1000) { Random.nextLong() }.maxOrNull()

            val threadName = Thread.currentThread().name
            println("Running on thread: $threadName")
        }
    }
}