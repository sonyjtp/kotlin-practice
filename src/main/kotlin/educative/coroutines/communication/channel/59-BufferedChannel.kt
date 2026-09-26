package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
    val channel = produce(capacity = 2) {
        repeat(20) { index ->
            send(index)
            println("${Thread.currentThread().name} sent $index")
        }
    }

    for (element in channel) {
        delay(100.milliseconds)
        println("${Thread.currentThread().name} received $element")
    }
}