package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun main(): Unit = coroutineScope {
    // or produce(capacity = Channel.RENDEZVOUS) {} default type is Rendezvous
    // returns immediately like launch and the Channel is created.
    val channel = produce {
        repeat(20) { index ->
            // Right when the producer calls send(0) , the consumer consumes it.
            // Then the "producer's send(0) is returned, about to println".
            //  The consumer's callback runs in between the producer calling send()
            //  and that same call "returning" back to the producer's own code.
            // ** The value 0 was already fully "produced, but not sent" before send(0) was ever called **
            send(index)
            println("${Thread.currentThread().name} produced $index")
        }
    }

    channel.consumeEach {
        println("${Thread.currentThread().name} consumed $it")
    }
}