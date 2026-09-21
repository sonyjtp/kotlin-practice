package org.sony.educative.coroutines.state

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class User41(val name: String)

interface NetworkService {
    suspend fun fetchUser(id: Int): User41
}

class MockNetworkService : NetworkService {
    override suspend fun fetchUser(id: Int): User41 {
        delay(2.milliseconds)
        return User41("User$id")
    }
}

private class UserDownloader(
    private val api: NetworkService
) {
    private val users = mutableListOf<User41>()
    private val dispatcher = Dispatchers.IO.limitedParallelism(1)

    suspend fun downloaded(): List<User41> =withContext(dispatcher) { users.toList() }

    suspend fun fetchUser(id: Int) = withContext(dispatcher) { // only 1 thread fetches and updates
        val newUser = api.fetchUser(id)
        users += newUser
    }
}

suspend fun main() {
    val downloader = UserDownloader(MockNetworkService())
    coroutineScope {
        repeat(1_000_000) {
            launch {
                downloader.fetchUser(it)
            }
        }
    }
    print(downloader.downloaded().size) // ~1000000
}