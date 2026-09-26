package org.sony.educative.coroutines.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.sony.User
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds


@OptIn(ExperimentalCoroutinesApi::class)
class TestingFunctionsPropertiesTest {

    @Test
    fun `should show progress bar when sending data`() = runTest { // StandardTestDispatcher
        val database = FakeDatabase()
        val vm = UserViewModel(database)

        // since runTest uses a StandardTestDispatcher under the hood, this doesn't run anything yet.
        // It just enqueues the coroutine's start as a task at virtual t=0.
        launch { vm.sendUserData() }

        assertEquals(false, vm.progressBar) // initial value
        //  moves the clock 0 → 1000. " advanceTimeBy runs anything scheduled strictly before the target time."
        //  The launch's start task, queued at t=0, qualifies (0 < 1000), so it runs: sendUserData() begins,
        // calls  userDatabase.getUserData(), which calls delay(1000ms) — registering a new event for
        // t = 0 + 1000 = 1000 and suspending.  Since that event lands exactly on the new boundary (t=1000),
        // advanceTimeBy stops without running it (the half-open-range rule from before).
        advanceTimeBy(1000.milliseconds)
        // getUserData() hasn't even returned yet — it's suspended inside its own delay(),
        // so sendUserData() never reached progressBar = true.
        assertEquals(false, vm.progressBar)
        //  drains everything due at t=1000 (inclusive). This resumes getUserData()'s delay, returning the User.
        //  sendUserData(): progressBar = true executes
        //  calls userDatabase.sendUserData(user.copy(age = 46)), which itself calls delay(1000ms) —
        //  registering a new event for t  = 1000 + 1000 = 2000 and suspending again.
        //  runCurrent() checks for more work at t≤1000; finds none (next event is at 2000), so it stops.
        runCurrent()
        assertEquals(true, vm.progressBar)
        //  repeatedly drains everything until the scheduler has nothing left. It fast-forwards to t=2000,
        //  runs the pending sendUserData() delay-resumption, which returns from FakeDatabase.sendUserData,
        //  letting UserViewModel.sendUserData() continue to its last line: progressBar = false.
        //  The launch's coroutine then completes; nothing left to run.
        advanceUntilIdle()
        assertEquals(false, vm.progressBar)
    }
}

class FakeDatabase: UserDatabase {
    override suspend fun getUserData(): User {
        delay(1000.milliseconds)
        return User(1, "John", 45)
    }

    override suspend fun sendUserData(user: User) {
        delay(1000.milliseconds)
        // updated
    }
}