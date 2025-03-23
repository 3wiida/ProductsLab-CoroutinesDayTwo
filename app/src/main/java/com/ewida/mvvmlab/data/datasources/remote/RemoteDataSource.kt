package com.ewida.mvvmlab.data.datasources.remote

class RemoteDataSource(private val apiServices: ApiServices) {
    suspend fun getAllProducts()  = apiServices.getAllProducts()
}