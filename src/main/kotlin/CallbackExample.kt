package org.sony

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface CallBack<T> {
    fun onSuccess(result: T)
    fun onError(exception: Exception)
}

data class User(val id: Long, val name: String, val age: Int)

fun getUser(id: Long, callback: CallBack<User>) {
    val user = when (id) {
        1L -> User(id, "John", 30)
        2L -> User(id, "Jane",  35)
        else -> User(id, "None", 40)
    }
    callback.onSuccess(user)
}

suspend fun main () {
    print(fetchUser(1L))
}


suspend fun fetchUser(id: Long): User {
    return suspendCancellableCoroutine { continuation ->
        getUser(id, object : CallBack<User> {
            override fun onSuccess(result: User) {
                continuation.resume(result)
            }
            override fun onError(exception: Exception) {
                continuation.resumeWithException(exception)
            }
        })
    }
}

