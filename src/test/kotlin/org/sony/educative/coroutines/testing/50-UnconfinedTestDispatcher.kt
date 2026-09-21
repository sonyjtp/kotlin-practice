package org.sony.educative.coroutines.testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.time.Duration.Companion.milliseconds












// The two dispatchers are driven by two completely separate virtual clocks.
@ExperimentalCoroutinesApi
fun main() {
    // doesn't print anything until the scheduler methods are called
    val standardTestDispatcher = StandardTestDispatcher()
    val unconfinedTestDispatcher = UnconfinedTestDispatcher()
    CoroutineScope(standardTestDispatcher).launch {
        print("A") // 2
        delay(1.milliseconds)
        print("B") //3
    }
    // isDispatchNeeded() = false (eager) means the coroutine body runs immediately, inline, synchronously
    CoroutineScope(unconfinedTestDispatcher).launch {
        print("C") // 1. will always be printed first as this dispatcher was initialized eagerly
        delay(1.milliseconds)
        // never printed ; only once advanceTimeBy(1) actually moves the virtual clock past that target (1 ms)
        // does the resumption become eligible to run — and "D" printed.
        print("D")
    }
    standardTestDispatcher.scheduler.advanceTimeBy(delayTimeMillis = 1)
    standardTestDispatcher.scheduler.runCurrent()
}