package com.ewida.mvvmlab

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {
    println("From Channel")
    val names = listOf("Mahmoud", "Ibrahem", "Ewida")

    runBlocking {
        produceNames(names).consumeEach { name ->
            println(name)
        }
    }

    println("From Flow")
    runBlocking {
        getNamesFlow(names).collect { name ->
            println(name)
        }
    }
}

fun produceNames(names: List<String>) = GlobalScope.produce<String> {
    names.forEach { name ->
        send(name)
        delay(1000)
    }
}

fun getNamesFlow(names: List<String>) = flow<String> {
    names.forEach { name ->
        emit(name)
        delay(1000)
    }
}