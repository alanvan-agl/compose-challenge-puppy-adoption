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
package com.example.androiddevchallenge.data.auth

import android.util.Log
import com.example.androiddevchallenge.utils.tryOrNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class CredentialsInterceptor(
    private val baseUrl: String,
    private val credentialsRepository: CredentialsContract.Repository
) : Interceptor {

    companion object {
        private const val AUTH_HEADER_KEY = "Authorization"
        private const val BEARER_PREFIX = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().let { request ->
            if (request.url.toString().startsWith(baseUrl)) {
                request.newBuilder().apply {
                    tryOrNull {
                        runBlocking {
                            credentialsRepository.get().collect {
                                Log.d("dmdmdm", "token ${it.value}")
                                addHeader(AUTH_HEADER_KEY, "$BEARER_PREFIX ${it.value}")
                            }
                        }
                    }
                }.build()
            } else request
        }
        return chain.proceed(request)
    }
}
