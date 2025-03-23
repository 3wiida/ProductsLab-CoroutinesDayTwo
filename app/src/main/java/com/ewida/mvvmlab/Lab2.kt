package com.ewida.mvvmlab


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

fun main() {
    val evenNumbersFlow = flow {
        (0..20 step 2).forEach { num ->
            delay(1000)
            emit(num)
        }
    }

    runBlocking {
        evenNumbersFlow.take(10).collect { num ->
            println(num)
        }
    }

}