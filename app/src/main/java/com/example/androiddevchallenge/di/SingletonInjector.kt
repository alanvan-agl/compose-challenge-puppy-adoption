package com.example.androiddevchallenge.di

import android.content.Context
import com.example.androiddevchallenge.data.repository.AppTokenRepository
import com.example.androiddevchallenge.data.repository.TokenRepository
import com.example.androiddevchallenge.data.network.ApiRequestInterceptor
import com.example.androiddevchallenge.data.network.AuthRequestInterceptor
import com.example.androiddevchallenge.data.network.FlowCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class SingletonInjector(val context: Context) {
    private companion object {
        const val BASE_URL = "https://api.petfinder.com"
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiRequestInterceptor)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
    }

    fun provideRetrofit() = retrofit

    private val apiRequestInterceptor by lazy {
        ApiRequestInterceptor(BASE_URL, tokenRepository)
    }

    private val authOkhttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authRequestInterceptor)
            .build()
    }

    private val authRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authOkhttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
    }

    private val authRequestInterceptor by lazy {
        AuthRequestInterceptor(BASE_URL)
    }

    private val tokenRepository: TokenRepository by lazy {
        AppTokenRepository(authRetrofit.create())
    }
}
