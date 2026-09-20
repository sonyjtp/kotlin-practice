package org.sony.educative.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration.Companion.milliseconds

// https://www.educative.io/courses/mastering-kotlin-coroutines/challenge-exception-handling
 class MyException : Exception()

suspend fun main(): Unit = supervisorScope {
    val a = async<String> {
        delay(1000.milliseconds)
        throw MyException()
    }
    val b = async {
        delay(2000.milliseconds)
        "Educative Inc."
    }
    try {
        println(a.await())
    } catch (e: Exception) {
        println(e)
    }
    println(b.await())
}