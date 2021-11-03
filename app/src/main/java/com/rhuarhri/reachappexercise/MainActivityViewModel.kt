package com.rhuarhri.reachappexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhuarhri.reachappexercise.online.Online
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repo : MainActivityRepository) : ViewModel() {

    init {
        setup()
    }

    val productListState = repo.productListLiveData

    private fun setup() {
        viewModelScope.launch {
            repo.getProducts()
        }
    }

}

class MainActivityRepository @Inject constructor(val online : Online) {

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