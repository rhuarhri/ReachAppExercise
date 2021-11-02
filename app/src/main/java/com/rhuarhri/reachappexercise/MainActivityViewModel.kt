package com.rhuarhri.reachappexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhuarhri.reachappexercise.online.Online
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    val repo : MainActivityRepository

    init {
        val baseURL = "https://apps-tests.s3-eu-west-1.amazonaws.com/android/"

        //val cache = OnlineCache(context)

        val retrofit = Retrofit.Builder()
            //.client(cache.cache)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()

        val online = Online(retrofit)
        repo = MainActivityRepository(online)
        setup()
    }

    val productListState = repo.productListLiveData

    private fun setup() {
        viewModelScope.launch {
            repo.getProducts()
        }
    }

}

class MainActivityRepository(val online : Online) {

    val productListLiveData : LiveData<List<ProductItem>> =
        Transformations.map(online.currentProductList) {found ->
            val productList = mutableListOf<ProductItem>()

            if (found != null && found.products.isNullOrEmpty() == false) {
                for (product in found.products) {
                    val foundName = product.name
                    val foundDescription = product.description
                    val foundImageURL = product.image
                    val foundPrice = product.price

                    if (foundDescription != null && foundName != null && foundImageURL != null && foundPrice != null) {
                        val foundItem = ProductItem(
                            name = foundName, description = foundDescription,
                            imageURL = foundImageURL, price = foundPrice
                        )

                        productList.add(foundItem)
                    }
                }
            }

            productList
        }

    suspend fun getProducts() {
        online.getProducts()
    }

}

data class ProductItem(val name : String, val description : String,
                       val imageURL: String, val price : Double)