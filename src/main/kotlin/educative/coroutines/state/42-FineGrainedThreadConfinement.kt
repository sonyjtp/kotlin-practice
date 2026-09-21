package org.sony.educative.coroutines.state

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private class UserDownloader2(
    private val api: NetworkService
) {
    private var users = mutableListOf<User41>()
    private val dispatcher = Dispatchers.IO.limitedParallelism(1)

    suspend fun downloaded(): List<User41> =withContext(Dispatchers.Default) { users.toList() }

    suspend fun fetchUser(id: Int) {
        val newUser = api.fetchUser(id)
        withContext(dispatcher) {
            users += newUser // only 1 thread updates
        }
    }
}

suspend fun main() {
    val downloader = UserDownloader2(MockNetworkService())
    coroutineScope {
        repeat(1_000_000) {
            launch {
                downloader.fetchUser(it)
            }
        }
    }
    print(downloader.downloaded().size) // ~1000000
}

