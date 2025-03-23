package com.ewida.mvvmlab

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {

    val names = listOf("Mahmoud", "Mohamed", "Ali")
    val stateFlow = MutableStateFlow("")

    names.forEach { name ->
        stateFlow.emit(name)
    }

    stateFlow.collect { name ->
        println("State Flow Collector => $name")
    }

}

