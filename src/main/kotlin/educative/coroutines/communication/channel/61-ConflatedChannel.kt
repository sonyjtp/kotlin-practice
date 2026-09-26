package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.CONFLATED) {
        repeat(5) { index ->
            send(index).also { println("Produced $index") }
            delay(10.milliseconds)

        }
    }
    delay(25.milliseconds)
    for (element in channel) {
        // only the last value is consumed. Due to the 25 ms delay, the consumer does not see all the values
        // but only those that was overwritten right before the time it consumed
        println("Consumed $element")
//        delay(100.milliseconds)
    }
}