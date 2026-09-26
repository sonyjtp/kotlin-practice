package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main(): Unit = coroutineScope {
    val channel = Channel<Int>()
    launch  {
        repeat(5) {
            delay(1.milliseconds)
            channel.send(it * 2)
            println("${Thread.currentThread().name} produced $it * 2")
        }
    }
    launch  {
        repeat(5) { // or channel.consumeEach { element -> }
            val received = channel.receive()
            println("${Thread.currentThread().name} received $received")
        }
    }
}