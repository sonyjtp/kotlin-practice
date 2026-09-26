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
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        onUndeliveredElement = {
            println("Undelivered element $it")
        }
    )
    launch {
        repeat(times = 5) {
            channel.send(it)
            // Produced 0, 1 printed due to the 15 ms delay before consumption. Now the buffer is full
            println("Produced $it")
            delay(11.milliseconds)
        }
        channel.close()
    }
    delay(15.milliseconds)
    for (element in channel) {
        println("Consumed $element") // 2. 0 is consumed
        delay(300.milliseconds) // consumer is much slower than producer, so the buffer overflows
    }
}