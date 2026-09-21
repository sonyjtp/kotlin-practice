package org.sony.educative.coroutines.context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

suspend fun printName() {
    val contextName = currentCoroutineContext()[CoroutineName]?.name ?: "unknown context"
    println(contextName)
}

suspend fun main() = withContext(CoroutineName("outer")) {
    printName() // 1. outer
    launch (CoroutineName("launch")) {
        delay(500.milliseconds)
        printName() // 3. launch
    }
    delay(10.milliseconds)
    printName() // 2. outer
}