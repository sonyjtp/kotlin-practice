package org.sony.educative.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

// https://www.educative.io/courses/mastering-kotlin-coroutines/adding-emptying-and-subtracting-elements-and-folding-contexts
fun main() {
    val ctx1: CoroutineContext = CoroutineName("coroutineContext1")
    println(ctx1[CoroutineName]?.name) // coroutineContext1
    println(ctx1[Job]?.isActive) // null

    val ctx2: CoroutineContext = Job()
    println(ctx2[CoroutineName]?.name) // null
    println(ctx2[Job]?.job?.isActive) // true

    val ctx3 = ctx1 + ctx2
    println(ctx3[CoroutineName]?.name) // coroutineContext1
    println(ctx3[Job]?.job?.isActive) // true

    val ctx4: CoroutineContext = CoroutineName("coroutineContext4")
    val ctx5 = ctx1 + ctx4
    println(ctx5[CoroutineName]?.name) // coroutineContext4
    val ctx6 = ctx4 + ctx1
    println(ctx6[CoroutineName]?.name) // coroutineContext1
    val empty: CoroutineContext = EmptyCoroutineContext
    println(empty[CoroutineName]?.name) // null
    val ctx7 = ctx1 + empty
    println(ctx7[CoroutineName]?.name) // coroutineContext1
    val ctx8 = (ctx7 + Job()).minusKey(CoroutineName)
    println(ctx8[CoroutineName]?.name) // null
    println(ctx8[Job]?.job?.isActive) // true

    val ctx9 = CoroutineName("coroutineContext9") + Job()
    ctx9.fold("") { acc, element -> "$acc$element" }.also (::println) // CoroutineName(coroutineContext9)JobImpl{Active}@....

}