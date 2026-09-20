package org.sony.educative.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit  = runBlocking{
    val name = CoroutineName("launch")
    val job = Job()
    val job1 = launch (name + job) {
        val childName = coroutineContext[CoroutineName]
        println(childName == name) // 1. true
        val childJob = coroutineContext[Job]
        println(childJob == job.children.firstOrNull()) //  2. true
        println("Child job: ${coroutineContext[Job]}") // 3. Child job: StandaloneCoroutine{Active}@x
    }
    job1.join()
    println(job1.job) // 4. StandaloneCoroutine{Completed}@x
    println(coroutineContext[Job]) // 5. BlockingCoroutine{Active}@y
}