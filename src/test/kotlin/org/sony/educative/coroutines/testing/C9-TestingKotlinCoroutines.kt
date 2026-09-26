package org.sony.educative.coroutines.testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
fun main() {
    CoroutineScope(StandardTestDispatcher()).launch {
        print("First coroutine started")
        delay(2000.milliseconds)
        print("First coroutine ended")
    }
    CoroutineScope(UnconfinedTestDispatcher()).launch {
        print("Second coroutine started")
        delay(2000.milliseconds)
        print("Second coroutine ended")
    }
}