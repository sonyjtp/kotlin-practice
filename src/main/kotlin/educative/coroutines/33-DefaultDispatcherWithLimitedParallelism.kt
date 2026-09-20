package org.sony.educative.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


suspend fun main() = coroutineScope {
    println("Available processors: ${Runtime.getRuntime().availableProcessors()}")
    val dispatcher = Dispatchers.Default.limitedParallelism(5)
    repeat(15) {
        launch (dispatcher) {
            println("Running on thread ${Thread.currentThread().name}")
        }
    }
}