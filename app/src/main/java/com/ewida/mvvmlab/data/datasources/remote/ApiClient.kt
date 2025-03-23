package com.ewida.mvvmlab.data.datasources.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://dummyjson.com"

    fun getServices() = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
    }.build().create(ApiServices::class.java)
}