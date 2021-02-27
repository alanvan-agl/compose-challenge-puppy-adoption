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
package com.example.androiddevchallenge.data.network

import com.example.androiddevchallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Response

class AuthRequestInterceptor(private val baseUrl: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request().let { request ->
                if (request.url.toString().startsWith(baseUrl)) {
                    request.newBuilder().apply {
                        post(
                            MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("grant_type", "client_credentials")
                                .addFormDataPart("client_id", BuildConfig.API_KEY)
                                .addFormDataPart("client_secret", BuildConfig.SECRET)
                                .build()
                        )
                    }.build()
                } else request
            }
        )
}
