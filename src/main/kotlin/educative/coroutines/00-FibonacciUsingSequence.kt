package org.sony.educative.coroutines

fun main() {
    println(getNextFibonacciNumber().take(15).toList().joinToString(", "))
}

fun getNextFibonacciNumber() = sequence {
    var a = 0L
    var b = 1L
    var iter = 0
    while (true) {
        yield(a)
        val next = a + b
        a = b
        b = next
        iter += 1
    }
}