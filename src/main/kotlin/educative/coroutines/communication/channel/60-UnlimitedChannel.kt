package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.UNLIMITED) {
        repeat(5) { index ->
            send(index)
            println("${Thread.currentThread().name} sent $index")
        }
    }

    delay(100.milliseconds)
    for (element in channel) {
        println("${Thread.currentThread().name} received $element")
    }
}