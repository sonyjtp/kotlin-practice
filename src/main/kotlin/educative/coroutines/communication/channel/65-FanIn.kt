package org.sony.educative.coroutines.communication.channel

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds









suspend fun sendString(
    channel: SendChannel<String>,
    text: String,
    time: Duration
) {
    while (true) {
        delay(time)
        channel.send(text)
    }
}

fun main() = runBlocking {
    val channel = Channel<String>()
    launch { sendString(channel, "foo", 200.milliseconds) }
    launch { sendString(channel, "bar", 500.milliseconds) }
    repeat(6) {
        println(channel.receive())
    }
    coroutineContext.cancelChildren() // cancel called because of the infinite while loop
}