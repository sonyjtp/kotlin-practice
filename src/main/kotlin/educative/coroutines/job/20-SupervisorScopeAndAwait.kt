package org.sony.educative.coroutines.job

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration.Companion.milliseconds

suspend fun main() = supervisorScope {
    val a = async<String> {
        delay(1000.milliseconds)
        throw Exception("Some Error!") //1 - throws exception after 1s
    }
    val b = async {
        delay(2000.milliseconds)
        "Some Success!" // 2- returned after 2s
    }
    delay(100.milliseconds)
    try {
        println(a.await()) // 3 - not printed due to exception in 1
    } catch (e: Exception) {
        println("Exception!: ${e.message}") // 4 - printed after 1s due to exception in 1
    }
    println(b.await()) // 5 - printed after 2s. Not canceled as the async call's parent is in supervisorScope
}