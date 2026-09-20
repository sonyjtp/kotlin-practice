package org.sony.educative.coroutines

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

private const val NUMBER_OF_THREADS = 4
private val dispatcher = Executors.newFixedThreadPool(NUMBER_OF_THREADS).asCoroutineDispatcher()
suspend fun main(): Unit = coroutineScope {
    dispatcher.use {
        withContext(it) {
            repeat(10) {
                launch {
                    println("I'm working in thread ${Thread.currentThread().name}")
                }
            }
        }
    }
}