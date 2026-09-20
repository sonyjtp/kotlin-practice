package org.sony.educative.coroutines

import kotlinx.coroutines.CoroutineName


fun main() {
    val educativeCtx = CoroutineName("Educative")
    println(educativeCtx[CoroutineName]?.name)
    val incCtx = CoroutineName("Inc.")
    println(incCtx[CoroutineName]?.name)
    val ctx3 = educativeCtx + incCtx
    println(ctx3[CoroutineName]?.name)
}


