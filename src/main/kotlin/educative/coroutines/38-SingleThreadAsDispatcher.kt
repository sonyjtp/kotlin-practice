package org.sony.educative.coroutines

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.time.Duration.Companion.milliseconds

private var i = 0
private val dispatcher = Executors.newSingleThreadExecutor()
    .asCoroutineDispatcher()

suspend fun main(): Unit = coroutineScope {
    dispatcher.use { disp ->
        repeat(10_000) {
            launch(disp) { // or Default
                i++
            }
        }
    }

    delay(1000.milliseconds)
    println(i)
}