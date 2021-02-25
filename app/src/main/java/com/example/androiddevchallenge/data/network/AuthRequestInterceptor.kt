package com.example.androiddevchallenge.data.network

import com.example.androiddevchallenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Response

class AuthRequestInterceptor(private val baseUrl: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().let { request ->
            if (request.url().toString().startsWith(baseUrl)) {
                request.newBuilder().apply {
                    addHeader("Content-type", "application/json")
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
        })
}
