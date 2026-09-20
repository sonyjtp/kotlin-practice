package org.sony.educative.coroutines

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

suspend fun main() = coroutineScope {
    val job = Job()
    println(job) // 1. JobImpl{Active}@...
    job.complete()
    println(job) // 2. JobImpl{Completed}@...

    val activeJob = launch {
        delay(1000.milliseconds)
    }
    println(activeJob) // 3. StandaloneCoroutine{Active}@...
    activeJob.join()
    println(activeJob) // 4. StandaloneCoroutine{Completed}@...

    val lazyJob = launch (start = CoroutineStart.LAZY) {
        delay(1000.milliseconds)
    }
    println(lazyJob) // LazyStandaloneCoroutine{New}@...
    lazyJob.join()
    println(lazyJob) // LazyStandaloneCoroutine{Completed}@
}