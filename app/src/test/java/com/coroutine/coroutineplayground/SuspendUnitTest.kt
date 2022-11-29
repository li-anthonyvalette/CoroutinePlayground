package com.coroutine.coroutineplayground

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test

fun methodToTest(): Int {
    return 46
}


@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {


    // suspend method need to be executed in a coroutine context
    @Test
    fun verifyMethodToTestReturnAnOddValue() {
        assert(methodToTest() % 2 == 0)
    }


    // GlobalScope
    @Test
    fun verifyMethodToTestReturnAnOddValueWithGlobalScope() {
        GlobalScope.launch {
            assert(methodToTest() % 2 == 0)
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



