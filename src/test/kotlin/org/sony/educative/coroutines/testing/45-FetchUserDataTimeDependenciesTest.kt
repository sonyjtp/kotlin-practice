package org.sony.educative.coroutines.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
class FetchUserDataTimeDependenciesTest {

}

class FakeDelayedUserDataRepository : UserDataRepository {

    override suspend fun getProfile(): Profile {
        delay(1000.milliseconds)
        return Profile("Example description")
    }

    override suspend fun getName(): String {
        delay(1000.milliseconds)
        return "Ben"
    }

    override suspend fun getFriends(): List<Friend> {
        delay(1000.milliseconds)
        return listOf(Friend("some-friend-id-1"))
    }
}