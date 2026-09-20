package org.sony

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    suspendCoroutineExample()
}


fun suspendCoroutineExample() {
    runBlocking {
        println("Starting coroutine example")
        suspendCoroutine { continuation ->
            println("Suspending function")
            continuation.resume(1)
        }
        println("After")
    }
}
