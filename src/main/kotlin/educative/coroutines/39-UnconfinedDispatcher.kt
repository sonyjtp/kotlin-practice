package org.sony.educative.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds

//https://www.educative.io/courses/mastering-kotlin-coroutines/more-on-dispatchers#Unconfined-dispatcher
private var dispatcher1 = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
private var dispatcher2 = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

suspend fun main(): Unit =
    dispatcher1.use { disp1 ->
        // main switches its entire coroutine context to a fresh single-thread executor, thread pool-1-thread-1
        // Everything inside this block starts confined to that thread
        withContext(disp1) { // pool-1-thread-1
            // continuation is a mutable holder, it exists so the coroutine 2 lines below can later reach into and
            //  manually resume the next coroutine from the outside.
            var continuation: Continuation<Unit>? = null
            dispatcher2.use { disp2 ->
                withContext(disp1) {
                    launch(disp2) { //  pool-2-thread-1
                        delay(1000.milliseconds)
                        // After the delay, it resumes whatever continuation was stored in the shared variable — calling
                        // .resume(Unit) from current thread (pool-2-thread-1)
                        continuation?.resume(Unit)
                    }
                    //  Unlike a normal dispatcher, Unconfined doesn't schedule the coroutine onto its own thread pool —
                    //  it starts executing immediately, synchronously on pool-1-thread-1
                    launch(Dispatchers.Unconfined) {
                        println(Thread.currentThread().name) // prints pool-1-thread-1
                        // suspends and capture its own Continuation object into the continuation variable.
                        // Execution pauses — control returns up the call stack, and pool-1-thread-1 is now free to do
                        // other things.
                        suspendCancellableCoroutine {
                            continuation = it
                        }
                        // continuation.resume is called from pool-2-thread-1.  Because Unconfined coroutines resume on
                        // whatever  thread called resume,  execution continues here on pool-2-thread-1
                        println(Thread.currentThread().name) // prints pool-2-thread-1
                        // suspends again. Executors.newSingleThreadExecutor().asCoroutineDispatcher() wraps a plain
                        // ExecutorService — just a thread that runs submitted tasks. It has no built-in
                        // "run this in N milliseconds" capability (that requires a ScheduledExecutorService,
                        // which this isn't). So it doesn't implement Delay.
                        // fallback -  when the dispatcher in scope doesn't implement Delay itself, kotlinx.coroutines
                        // falls back to a shared, library-internal background thread — something like
                        // kotlinx.coroutines.DefaultExecutor
                        delay(1000.milliseconds)
                        println(Thread.currentThread().name) // prints kotlinx.coroutines.DefaultExecutor
                    }
                }

            }
        }
    }
