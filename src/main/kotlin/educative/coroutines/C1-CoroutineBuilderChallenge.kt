package org.sony.educative.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

//https://www.educative.io/courses/mastering-kotlin-coroutines/challenge-coroutine-builders


suspend fun main() {

    coroutineScope {
        launch {
            delay(2000.milliseconds)
            println("EDUCATIVE!!")
        }
    }
    println("Educative !")
}
