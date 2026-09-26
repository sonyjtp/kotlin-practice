package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    val channel = Channel<Int>(
        capacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    launch {
        repeat(5) {
            channel.send(it)
            delay(100.milliseconds)
            println("Produced $it")
        }
        channel.close()
    }
    delay(100.milliseconds)
    for (element in channel) {
        println("Consumed $element")
        delay(100.milliseconds)
    }
}