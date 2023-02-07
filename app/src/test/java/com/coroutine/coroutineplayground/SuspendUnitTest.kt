package com.coroutine.coroutineplayground

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test

suspend fun methodToTest(): Int {
    delay(5000)
    return withContext(Dispatchers.Default){
        46
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
class SuspendUnitTest {


    // suspend method need to be executed in a coroutine context
    @Test
    fun verifyMethodToTestReturnAnOddValue() {
       //assert(methodToTest() % 2 == 0)
    }










    // GlobalScope
    @Test
    fun verifyMethodToTestReturnAnOddValueWithGlobalScope() {
        GlobalScope.launch {
            assert(methodToTest() % 2 != 0)
        }
    }








    // runBlocking
    @Test
    fun verifyMethodToTestReturnAnOddValueWithRunBlocking() {
        runBlocking {
            assert(methodToTest() % 2 == 0)
        }
    }








    //runTest
    @Test
    fun verifyMethodToTestReturnAnOddValueWithRunTest() = runTest {
        assert(methodToTest() % 2 == 0)
    }

}



