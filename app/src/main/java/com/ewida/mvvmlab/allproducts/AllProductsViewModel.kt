package com.ewida.mvvmlab.allproducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ewida.mvvmlab.data.model.Product
import com.ewida.mvvmlab.data.model.ProductsResponse
import com.ewida.mvvmlab.data.repository.ProductsRepository
import com.ewida.mvvmlab.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class AllProductsViewModel(private val repo: ProductsRepository) : ViewModel() {

    private val _allProductsResponse = MutableStateFlow<Response<*>>(Response.Loading)
    val allProductsResponse = _allProductsResponse.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _allProductsResponse.emit(Response.Success(data = repo.getAllProducts()))
            } catch (ex: Exception) {
                _allProductsResponse.emit(Response.Failure(error = ex.message.toString()))
            }
        }
    }

    fun addToFav(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            val isAdded = repo.addToFav(product)
            if (isAdded > 0) {
                _message.value = "Item Added Successfully"
            } else {
                _message.value = "Cant Add Item To Fav"
            }
        }
    }

    fun resetMsg() {
        _message.value = ""
    }

    class Factory(private val repo: ProductsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
            return AllProductsViewModel(repo = repo) as T
        }
    }
}