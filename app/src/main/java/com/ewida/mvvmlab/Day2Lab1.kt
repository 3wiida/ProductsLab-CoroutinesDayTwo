package com.ewida.mvvmlab

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    val names = listOf("Mahmoud", "Mohamed", "Ali")
    val sharedFlow = MutableSharedFlow<String>()

    launch {
        sharedFlow.collect { name ->
            println("Subscriber 1 => $name")
        }
    }

    launch {
        sharedFlow.collect { name ->
            println("Subscriber 2 => $name")
        }
    }

    names.forEach { name->
        sharedFlow.emit(name)
    }
}

