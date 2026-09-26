package org.sony.educative.coroutines.testing

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class `53-FetchUserUseCase`(
    private val userRepo: UserDataRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun fetchUserData() = withContext(ioDispatcher) {
        val name = async { userRepo.getName() }
        val friends = async { userRepo.getFriends() }
        val profile = async { userRepo.getProfile() }
        User45(
            name = name.await(),
            friends = friends.await(),
            profile = profile.await()
        )
    }
}