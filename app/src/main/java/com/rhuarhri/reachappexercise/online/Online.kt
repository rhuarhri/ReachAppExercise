package com.rhuarhri.reachappexercise.online

import androidx.lifecycle.MutableLiveData
import com.rhuarhri.reachappexercise.online.json.Product
import com.rhuarhri.reachappexercise.online.json.ProductList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.http.GET
import javax.inject.Inject

class Online (val retrofit: Retrofit){

    val currentProductList : MutableLiveData<ProductList?> = MutableLiveData<ProductList?>()

    suspend fun getProducts() {
        println("getting product list")
        val foundProductList : ProductList? = try {
            val onlineInterface = retrofit.create(OnlineInterface::class.java)
            val response = onlineInterface.getProducts().awaitResponse()
            if (response.isSuccessful == true) {
                if (response.body() != null) {
                    println("found result")
                }
                response.body()
            } else {
                println("response failed")
                null
            }
        } catch (e : Exception) {
            println("response error error was ${e.toString()}")
            null
        }

        currentProductList.value = foundProductList
    }

}

interface OnlineInterface {
    @GET("products.json")
    fun getProducts(): Call<ProductList>
}