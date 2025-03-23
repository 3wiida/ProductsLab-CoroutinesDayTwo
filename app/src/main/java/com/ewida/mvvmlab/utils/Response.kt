package com.ewida.mvvmlab.utils

import com.ewida.mvvmlab.data.model.Product

sealed class Response<T> {
    data object Loading : Response<Nothing>()
    data class Failure(val error: String) : Response<Nothing>()
    data class Success<T>(val data: T) : Response<T>()
}