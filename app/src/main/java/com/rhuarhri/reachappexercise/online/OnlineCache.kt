package com.rhuarhri.reachappexercise.online

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.*
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OnlineCache @Inject constructor(val context: Context) {

    val cache : OkHttpClient
    init {
        cache = getClient()
    }

    private  fun getClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .cache(getCache())
            .addNetworkInterceptor(getNetworkInterceptor())
            .addInterceptor(getOfflineInterceptor())
            .build()
    }

    private fun getCache() : Cache {
        val cacheSize : Long = (5 * 1024 * 1024).toLong() //5 MB
        return Cache(File(context.cacheDir, "appCache"), cacheSize)
    }

    private fun getNetworkInterceptor() : Interceptor {
        // this ensures that if the user decides to refresh the app. then the app
        // will have the option to get the data from the cache instead of the network if the
        // cache is new enough
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val response = chain.proceed(chain.request())

                val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

                return response.newBuilder()
                    .removeHeader("Pragma") //this could tell the request not to use caching in some case
                    .removeHeader("Cache-Control") //this would be some default cache control
                    .header("Cache-Control", cacheControl.toString())
                    .build()
            }

        }
    }

    private fun getOfflineInterceptor() : Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val response = chain.proceed(chain.request())

                if (checkNetworkConnection() == false) {
                    /*this runs every time a request for data is made
                    * but since that this interceptor is only interesting in getting information
                    * from the cache it needs to first check that there is a internet connection
                    * and from there know if the data should come from the cache*/
                    val cacheControl = CacheControl.Builder()
                        .maxAge(1, TimeUnit.DAYS)
                        .build()

                    return response.newBuilder()
                        .removeHeader("Pragma") //this could tell the request not to use caching in some case
                        .removeHeader("Cache-Control") //this would be some default cache control
                        .header("Cache-Control", cacheControl.toString())
                        .build()
                } else {
                    return response
                }
            }

        }
    }

    private fun checkNetworkConnection() : Boolean {
        val cm : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capability = cm.getNetworkCapabilities(cm.activeNetwork)

        val connected = if (capability != null) {
            capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            false
        }

        return connected
    }
}