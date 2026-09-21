package org.sony.educative.coroutines.testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
fun main() {
    //  nothing runs until the scheduler is explicitly driven.
    //  "Scheduled first, run only when the scheduler is advanced"
    val testDispatcher = StandardTestDispatcher()

    CoroutineScope(testDispatcher).launch {
        delay(2.milliseconds)
        print("Done")
    }

    CoroutineScope(testDispatcher).launch {
        delay(4.milliseconds)
        print("Done2")
    }

    CoroutineScope(testDispatcher).launch {
        delay(6.milliseconds)
        print("Done3")
    }

    (1..10).forEach { _ ->
        print(".")
        // moves the virtual clock forward by `delayTimeMillis` ms and runs any task that become due as the clock passes
        // through that millisecond. advanceTimeBy runs tasks scheduled strictly before the target time.
        //  It's not just "bump a counter" — it actually drives the scheduler's queue forward.
        // delayTimeMillis is just "the amount of clock the scheduler moves forward per call"
        testDispatcher.scheduler.advanceTimeBy(delayTimeMillis = 1)
        // runs anything scheduled at or before the current moment. In this example,
        // it never finds extra work (since advanceTimeBy already drained everything due),
        // but it's the idiomatic safety net — the standard pattern for single-stepping virtual time one tick at a
        // time. It makes sure nothing scheduled for "right now" gets left behind.
        testDispatcher.scheduler.runCurrent()
    }
}

//  ----- The measured timeline -----
//iter 1: t 0→1, nothing due
//iter 2: t 1→2, Done   printed at t=2   (coroutine 1's delay(2ms) elapses)
//iter 3: t 2→3, nothing due
//iter 4: t 3→4, Done2  printed at t=4   (coroutine 2's delay(4ms) elapses)
//iter 5: t 4→5, nothing due
//iter 6: t 5→6, Done3  printed at t=6   (coroutine 3's delay(6ms) elapses)
//iter 7-10: t 6→10, nothing due

//iter 1: t 0→1, "." printed
//iter 2: t 1→2, "." printed by advanceTimeBy, "Done"  printed at t=2 by the runCurrent call
//iter 3: t 2→3, "." printed
//iter 4: t 3→4, "." printed, "Done2"  printed at t=4 by the runCurrent call
//iter 5: t 4→5, "." printed
//iter 6: t 5→6, "." printed, Done3  printed at t=6 by the runCurrent call
//iter 7-10: t 6→10, "." printed