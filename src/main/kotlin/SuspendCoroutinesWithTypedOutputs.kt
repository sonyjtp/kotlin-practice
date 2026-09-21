package org.sony

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


suspend fun main() {
    println(suspendCoroutineWithTypedOutput("abc"))
    println(suspendCoroutineWithTypedOutput(1))
    println(suspendCoroutineWithTypedOutput(true))

}


suspend fun <T> suspendCoroutineWithTypedOutput(t: T): T = suspendCancellableCoroutine { continuation ->
    continuation.resume(t)
}
