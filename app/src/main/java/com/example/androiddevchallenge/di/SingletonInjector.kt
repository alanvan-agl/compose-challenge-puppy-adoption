package com.example.androiddevchallenge.di

import android.content.Context
import com.example.androiddevchallenge.data.auth.CredentialsContract
import com.example.androiddevchallenge.data.auth.CredentialsInterceptor
import com.example.androiddevchallenge.data.auth.CredentialsRepository
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

    private val authOkhttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthRequestInterceptor(BASE_URL))
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

    private val credentialsRepository: CredentialsContract.Repository = CredentialsRepository(authRetrofit.create())

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(CredentialsInterceptor(BASE_URL, credentialsRepository))
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
}
