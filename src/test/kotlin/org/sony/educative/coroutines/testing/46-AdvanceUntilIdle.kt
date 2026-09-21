package org.sony.educative.coroutines.testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi // TestCoroutineScheduler and StandardTestDispatcher  are experimental
fun main() {
    // creates a virtual clock. It tracks its own currentTime (starting at 0) and a queue of scheduled tasks,
    //  completely decoupled from real wall-clock time.
    // Nothing runs automatically here — we have to explicitly tell it to advance.
    val scheduler = TestCoroutineScheduler()
    // Work dispatched to it is enqueued, not run immediately. Nothing executes just because we launch something on
    //  it — it sits queued until the scheduler is explicitly told to run it.
    // (The StandardTestDispatcher function creates TestCoroutineScheduler by default,
    // so we don’t need to do so explicitly. This will also work: val testDispatcher = StandardTestDispatcher())
    val testDispatcher = StandardTestDispatcher(scheduler)

    // it just enqueues "start this coroutine" as a task for virtual time 0.
    CoroutineScope(testDispatcher).launch {
        println("Some work 1") // queued, not yet run
        //  delay does not actually sleep. It registers a virtual timer:
        //  "resume this coroutine when currentTime reaches now + 1000," then suspends.
        delay(1000.milliseconds)
        println("Some work 2")
        delay(1000.milliseconds)
        println("Coroutine done")
    }

    println("[${scheduler.currentTime}] Before")
    //  this is what actually drives everything.
    scheduler.advanceUntilIdle()
    println("[${scheduler.currentTime}] After")
}