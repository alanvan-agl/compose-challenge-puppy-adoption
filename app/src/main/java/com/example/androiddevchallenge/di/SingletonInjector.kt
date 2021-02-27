/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.di

import android.content.Context
import com.example.androiddevchallenge.data.auth.CredentialsContract
import com.example.androiddevchallenge.data.auth.CredentialsInterceptor
import com.example.androiddevchallenge.data.auth.CredentialsRepository
import com.example.androiddevchallenge.data.network.AuthRequestInterceptor
import com.example.androiddevchallenge.data.network.FlowCallAdapterFactory
import com.example.androiddevchallenge.data.puppy.PuppyContract
import com.example.androiddevchallenge.data.puppy.PuppyRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
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

    private val puppyRepository: PuppyContract.Repository = PuppyRepository(retrofit.create())

    fun providePuppyRepository(): PuppyContract.Repository = puppyRepository
}
