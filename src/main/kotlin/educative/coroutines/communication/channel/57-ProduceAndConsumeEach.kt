package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
    val channel =  produce {
        for (number in 1..5) {
            println("${Thread.currentThread().name} produced $number")
            delay(10.milliseconds)
            send(number)
        }
    }

    channel.consumeEach { println("${Thread.currentThread().name} consumed $it") }
}