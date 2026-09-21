package org.sony.educative.coroutines.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds


// see test in src/test/kotlin/org/sony/educative/coroutines/52-TestingFunctionsThatChangeDispatcher.kt
class CsvReader {
    suspend fun <T> readCsvBlocking(filename: String, java: Class<T>): T = withContext(Dispatchers.IO) {
        delay(100.milliseconds)
        java.cast(GameState("aGameState"))
    }
}

class SaveReader(val csvReader: CsvReader) {

    // Thread name switches to : DefaultDispatcher-worker-1 @coroutine#1
    suspend fun readSave(name: String): GameState = withContext(Dispatchers.IO) {
        csvReader.readCsvBlocking(name, GameState::class.java)
    }
}

data class GameState(val state: String)