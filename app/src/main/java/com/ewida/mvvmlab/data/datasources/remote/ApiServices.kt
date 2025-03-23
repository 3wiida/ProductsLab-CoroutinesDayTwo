package com.ewida.mvvmlab.data.datasources.remote

import com.ewida.mvvmlab.data.model.ProductsResponse
import retrofit2.http.GET

interface ApiServices {

    @GET("/products")
    suspend fun getAllProducts(): ProductsResponse
}