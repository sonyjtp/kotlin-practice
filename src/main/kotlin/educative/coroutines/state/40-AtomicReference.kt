package org.sony.educative.coroutines.state

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sony.User
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds


private val users = AtomicReference(listOf<User>())
private val apiService = ApiService()

suspend fun main() {
    coroutineScope {
        repeat(1000) { launch { fetchUser(it) } }
    }
    println(users.get().size)
}
suspend fun fetchUser(id: Int) {
    val user = execute { apiService.fetchUser(id) }
    // compare-and-swap (CAS) loop under the hood, i.e.
    // while (!compareAndSet(current, updated)) { get current and update }
    // "only write updated if the reference still equals current."
    //  If another thread snuck in and changed users between read and write, the CAS fails, and the loop retries —
    //  recomputing it + user against the now-current list — until it succeeds.
    users.getAndUpdate { it + user }
}
suspend fun execute(executeUserOperation: suspend () -> User): User = executeUserOperation()
private class ApiService {
    suspend fun fetchUser(id: Int): User {
        delay(2.milliseconds)
        return User(id.toLong(), "User$id", Random.nextInt(85))
    }
}