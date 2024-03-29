package com.coroutine.coroutineplayground

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherUnitTest {

    @Test
    fun standardTest() = runTest(StandardTestDispatcher()) {

        var coroutineCount = 0
        launch { coroutineCount++ }

        launch { coroutineCount++ }
        advanceUntilIdle()

        assertEquals(coroutineCount, 2)
    }

    @Test
    fun unconfinedTest() = runTest(UnconfinedTestDispatcher()) {
        var coroutineCount = 0
        launch { coroutineCount++ }
        launch { coroutineCount++ }

        assertEquals(coroutineCount, 2)
    }

    @Test
    fun standardTestWithDelay() = runTest(StandardTestDispatcher()) {

        var coroutineCount = 0
        launch { coroutineCount++ }
        advanceUntilIdle()

        launch {
            delay(100)
            coroutineCount++
        }
        advanceTimeBy(1100)

        assertEquals(coroutineCount, 2)
    }

    @Test
    fun complexStandardTest() = runTest(StandardTestDispatcher()) {

        var coroutineCount = 0
        launch {
            delay(100)
            coroutineCount++
        }
        advanceTimeBy(99)
        assertEquals(coroutineCount, 0)
        advanceTimeBy(2)
        assertEquals(coroutineCount, 1)

        launch { coroutineCount++ }
        advanceUntilIdle()

        assertEquals(coroutineCount, 2)
    }
}

