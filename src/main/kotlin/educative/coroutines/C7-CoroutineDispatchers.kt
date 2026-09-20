package org.sony.educative.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private var i = 0

suspend fun main() = coroutineScope {
    val dispatcher = Dispatchers.Default.limitedParallelism(1)
        repeat(10_000) {
            launch(dispatcher) {
            i += 1
        }
    }
    delay(2000.milliseconds)
    println(i)
}