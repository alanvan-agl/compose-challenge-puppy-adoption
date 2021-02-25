package com.example.androiddevchallenge.data.network

import com.example.androiddevchallenge.data.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class ApiRequestInterceptor(
    private val baseUrl: String,
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().let { request ->
            if (request.url().toString().startsWith(baseUrl)) {
                request.newBuilder().apply {
                    addHeader("Authorization", "Bearer ${tokenRepository.getToken()}")
                    addHeader("Content-type", "application/json")
                }.build()
            } else request
        })
}
