package org.sony.educative.coroutines.scope

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

fun CoroutineScope.log2(text: String) {
    val name = this.coroutineContext[CoroutineName]?.name
    println("[$name] $text")
}

fun main() = runBlocking(CoroutineName("Parent")) {
    log2("Before")
    withContext(CoroutineName("Child 1")) { // Context name changes
        delay(1000.milliseconds)
        log2("Hello 1")
    }
    withContext(CoroutineName("Child 2")) { // Context name changes again
        delay(1000.milliseconds)
        log2("Hello 2")
    }
    log2("After")
}
