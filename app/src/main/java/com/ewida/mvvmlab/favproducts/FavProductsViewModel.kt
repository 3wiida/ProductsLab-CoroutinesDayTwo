package com.ewida.mvvmlab.favproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.ewida.mvvmlab.data.model.Product
import com.ewida.mvvmlab.data.repository.ProductsRepository
import com.ewida.mvvmlab.utils.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class FavProductsViewModel(private val repo: ProductsRepository) : ViewModel() {

    private val _favProducts = MutableStateFlow<Response<*>>(Response.Loading)
    val favProducts = _favProducts.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun getFavProducts() {
        viewModelScope.launch {
            repo.getFavProducts().collect { products ->
                _favProducts.value = Response.Success(data = products)
            }
        }
    }

    fun removeFromFav(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            val isRemoved = repo.removeFromFav(product)
            if (isRemoved > 0) {
                _message.value = "Item Removed Successfully"
            }else{
                _message.value = "Can't Remove Item"
            }
        }
    }

    fun resetMsg() {
        _message.value = ""
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val repo: ProductsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
            return FavProductsViewModel(repo = repo) as T
        }
    }
}