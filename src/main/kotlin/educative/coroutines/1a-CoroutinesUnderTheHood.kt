package org.sony.educative.coroutines

import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration.Companion.milliseconds


private suspend fun myFunction1() {
    println("Before")
    delay(1000.milliseconds) // suspending
    println("After")
}

// The function above, behind the hood, actually becomes something like this:

private fun myFunction(continuation: Continuation<Unit>): Any {
    // The initial value of continuation is EMPTY_CONTINUATION. This line creates a brand new MyFunctionContinuation.
    // This continuation's initial label (continuation.label) is 0.
    val continuation = continuation as? MyFunctionContinuation
        ?: MyFunctionContinuation(continuation)
    if (continuation.label == 0) { // true
        println("Before")
        // update the label before calling the suspending function, so we know where to resume from when we return.
        continuation.label = 1
        // a task is scheduled in the background "scheduler" daemon thread to fire in 1000 ms, and delay immediately
        // returns COROUTINE_SUSPENDED, propagating "I paused" up to the caller.
        if (delay(1000, continuation) == COROUTINE_SUSPENDED){
            return COROUTINE_SUSPENDED
        }
    }
    // After 1000ms, the "scheduler" daemon thread wakes up and runs its scheduled task - continuation.resume(Unit)
    if (continuation.label == 1) {
        println("After") // continuation.resume
        return Unit
    }
    error("Impossible")
}

private class MyFunctionContinuation(
    val completion: Continuation<Unit>
) : Continuation<Unit> {
    override val context: CoroutineContext
        get() = completion.context
    var label = 0 //initial state
    var result: Result<Any>? = null

    override fun resumeWith(result: Result<Unit>) { // called when continuation.resume() is called
        this.result = result
        val res = try {
            val r = myFunction(continuation = this)
            if (r == COROUTINE_SUSPENDED) return
            Result.success(r as Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
        completion.resumeWith(res)
    }
}

fun main() {
    val EMPTY_CONTINUATION = object : Continuation<Unit> {
        override val context: CoroutineContext =
            EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            // This is root coroutine, we don't need anything in this example
        }
    }
    myFunction(EMPTY_CONTINUATION)
    Thread.sleep(2000)
    // Needed to prevent the main finishing immediately.
}

private val COROUTINE_SUSPENDED = Any()