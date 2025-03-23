package com.ewida.mvvmlab.data.datasources.local

import com.ewida.mvvmlab.data.model.Product

class LocalDataSource(private val dao: ProductsDao) {

    suspend fun addProduct(product: Product) = dao.insertProduct(product)

    suspend fun deleteProduct(product: Product) = dao.deleteProduct(product)

    fun getAllProducts() = dao.getAllProducts()
}