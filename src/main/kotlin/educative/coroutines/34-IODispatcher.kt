package org.sony.educative.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

// allows running on up to 64 concurrent threads. Could possibly run on more threads, but concurrency is
// capped at 64, or the number of cores, whichever is higher
suspend fun main() = withContext(Dispatchers.IO) {
    println("Number of available processors: ${Runtime.getRuntime().availableProcessors()}")
    repeat(100) {
        launch {
            delay(100L.milliseconds)
            println("Launching ${Thread.currentThread().name}")
        }
    }
}


//suspend fun main() = coroutineScope {
//    println("Number of available processors: ${Runtime.getRuntime().availableProcessors()}")
//    repeat(100) {
//        // allows running on up to 64 concurrent threads. Could possibly run on more threads, but concurrency is
//        // capped at 64, or the number of cores, whichever is higher
//        launch (Dispatchers.IO) {
//            delay(100L.milliseconds)
//            println("Launching ${Thread.currentThread().name}")
//        }
//    }
//}