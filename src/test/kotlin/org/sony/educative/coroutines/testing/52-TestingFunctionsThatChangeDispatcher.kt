package org.sony.educative.coroutines.testing

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlin.test.Test
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class TestSaveReader {
    @Test
    fun `should change dispatcher`() = runBlocking { // Thread name: Test worker @coroutine#1
        val csvReader = mockk<CsvReader>()
        var usedThreadName: String? = null // used to verify the thread name change later
        coEvery {
            csvReader.readCsvBlocking( // mock
                "aFileName",
                GameState::class.java
            )
        } coAnswers {
            usedThreadName = Thread.currentThread().name
            GameState("aGameState")
        }
        val saveReader = SaveReader(csvReader)
        // Thread name: Single Thread Context @coroutine#1
        withContext(newSingleThreadContext(name = "Single Thread Context")) {
            saveReader.readSave("aFileName")
        }
        val expectedPrefix = "DefaultDispatcher-worker-"
        usedThreadName?.let { assert(usedThreadName.startsWith(expectedPrefix)) } ?: fail("failed")
    }
}
