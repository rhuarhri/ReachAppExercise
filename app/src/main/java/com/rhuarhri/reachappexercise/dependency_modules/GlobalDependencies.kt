package com.rhuarhri.reachappexercise.dependency_modules

import android.content.Context
import com.rhuarhri.reachappexercise.DependencyBase
import com.rhuarhri.reachappexercise.MainActivityRepository
import com.rhuarhri.reachappexercise.online.Online
import com.rhuarhri.reachappexercise.online.OnlineCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlobalDependencies {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app : Context) : DependencyBase {
        return app as DependencyBase
    }

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context : Context) : Retrofit {
        val baseURL = "https://apps-tests.s3-eu-west-1.amazonaws.com/android/"

        //val cache = OnlineCache(context)

        return Retrofit.Builder()
            //.client(cache.cache)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOnline(@ApplicationContext context : Context) : Online {
        val retroFit = provideRetrofit(context)
        return Online(retroFit)
    }

    @Singleton
    @Provides
    fun provideMainActivityRepository(@ApplicationContext context : Context) : MainActivityRepository {
        val online = provideOnline(context)
        return MainActivityRepository(online)
    }

}