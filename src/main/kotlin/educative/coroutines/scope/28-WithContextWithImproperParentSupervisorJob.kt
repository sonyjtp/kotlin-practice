package org.sony.educative.coroutines.scope

import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

// SupervisorJob becomes the parent of the Job created by withContext which has no siblings
// So, when withContext's Job's child (1st launch) throws an exception, it cancels its sibling (2nd launch), too.
suspend fun main(): Unit = withContext(SupervisorJob()){
    launch {
        delay(1000.milliseconds)
        throw Exception()
    }
    launch {
        delay(1500.milliseconds)
        println("Will not be printed")
    }
}