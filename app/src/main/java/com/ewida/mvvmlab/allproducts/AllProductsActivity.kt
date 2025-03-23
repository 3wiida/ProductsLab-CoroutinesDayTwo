package com.ewida.mvvmlab.allproducts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ewida.mvvmlab.data.datasources.local.LocalDataSource
import com.ewida.mvvmlab.data.datasources.local.ProductsDatabase
import com.ewida.mvvmlab.data.datasources.remote.ApiClient
import com.ewida.mvvmlab.data.datasources.remote.RemoteDataSource
import com.ewida.mvvmlab.data.model.Product
import com.ewida.mvvmlab.data.model.ProductsResponse
import com.ewida.mvvmlab.data.repository.ProductsRepository
import com.ewida.mvvmlab.favproducts.FavProductsActivity
import com.ewida.mvvmlab.ui.theme.MVVMLabTheme
import com.ewida.mvvmlab.utils.Response

class AllProductsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = AllProductsViewModel.Factory(
            repo = ProductsRepository.getInstance(
                localDataSource = LocalDataSource(dao = ProductsDatabase.getInstance(this).getDao()),
                remoteDataSource = RemoteDataSource(apiServices = ApiClient.getServices())
            )
        )

        val viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[AllProductsViewModel::class.java]

        viewModel.getAllProducts()

        setContent {

            val productsResponse = viewModel.allProductsResponse.collectAsStateWithLifecycle()
            val message = viewModel.message.collectAsStateWithLifecycle()
            val snackBarHostState = remember { SnackbarHostState() }

            MVVMLabTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    }
                ) {
                    when(val response = productsResponse.value){
                        is Response.Failure -> {
                            Toast.makeText(this,response.error,Toast.LENGTH_SHORT).show()
                        }
                        Response.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator()
                            }
                        }
                        is Response.Success<*> -> {
                            response.data as ProductsResponse
                            AllProductsScreenContent(
                                products = response.data.products,
                                onButtonClicked = { product ->
                                    viewModel.addToFav(product)
                                },
                                onNavigateToFav = {
                                    startActivity(Intent(this, FavProductsActivity::class.java))
                                }
                            )
                        }
                    }
                }
            }

            LaunchedEffect(key1 = message.value) {
                if (viewModel.message.value.isNotEmpty()) {
                    snackBarHostState.showSnackbar(
                        message = viewModel.message.value,
                        duration = SnackbarDuration.Short
                    )
                }
                viewModel.resetMsg()
            }
        }
    }
}


