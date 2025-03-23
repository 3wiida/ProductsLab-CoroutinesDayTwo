package com.ewida.mvvmlab.favproducts

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ewida.mvvmlab.allproducts.AllProductsScreenContent
import com.ewida.mvvmlab.allproducts.AllProductsViewModel
import com.ewida.mvvmlab.data.datasources.local.LocalDataSource
import com.ewida.mvvmlab.data.datasources.local.ProductsDatabase
import com.ewida.mvvmlab.data.datasources.remote.ApiClient
import com.ewida.mvvmlab.data.datasources.remote.ApiServices
import com.ewida.mvvmlab.data.datasources.remote.RemoteDataSource
import com.ewida.mvvmlab.data.model.Product
import com.ewida.mvvmlab.data.repository.ProductsRepository
import com.ewida.mvvmlab.ui.theme.MVVMLabTheme
import com.ewida.mvvmlab.utils.Response

class FavProductsActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = FavProductsViewModel.Factory(
            repo = ProductsRepository.getInstance(
                localDataSource = LocalDataSource(dao = ProductsDatabase.getInstance(this).getDao()),
                remoteDataSource = RemoteDataSource(apiServices = ApiClient.getServices())
            )
        )

        val viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[FavProductsViewModel::class.java]

        viewModel.getFavProducts()

        setContent {
            val productsResponse = viewModel.favProducts.collectAsStateWithLifecycle()
            val message = viewModel.message.collectAsStateWithLifecycle()
            val snackBarHostState = remember { SnackbarHostState() }

            MVVMLabTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    }
                ) {
                    when(val response = productsResponse.value){
                        Response.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator()
                            }
                        }
                        is Response.Failure -> {
                            Toast.makeText(this,response.error, Toast.LENGTH_SHORT).show()
                        }
                        is Response.Success<*> -> {
                            response.data as List<Product>
                            FavProductsScreenContent(
                                products = response.data,
                                onButtonClicked = { product ->
                                    viewModel.removeFromFav(product = product)
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
