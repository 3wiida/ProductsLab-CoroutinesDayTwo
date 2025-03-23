package com.ewida.mvvmlab.data.repository

import com.ewida.mvvmlab.data.datasources.local.LocalDataSource
import com.ewida.mvvmlab.data.datasources.remote.RemoteDataSource
import com.ewida.mvvmlab.data.model.Product

class ProductsRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getAllProducts() = remoteDataSource.getAllProducts()

    suspend fun addToFav(product: Product) = localDataSource.addProduct(product)

    suspend fun removeFromFav(product: Product) = localDataSource.deleteProduct(product)

    fun getFavProducts() = localDataSource.getAllProducts()

    companion object {
        private var instance: ProductsRepository? = null
        fun getInstance(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): ProductsRepository {
            return instance ?: synchronized(this) {
                val repo = ProductsRepository(localDataSource, remoteDataSource)
                instance = repo
                repo
            }
        }
    }
}