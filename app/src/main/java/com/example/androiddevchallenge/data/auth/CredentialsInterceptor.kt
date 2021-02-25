package com.example.androiddevchallenge.data.auth

import com.example.androiddevchallenge.utils.tryOrNull
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.*

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
            if (request.url().toString().startsWith(baseUrl)) {
                request.newBuilder().apply {
                    tryOrNull {
                        runBlocking {
                            credentialsRepository.get().collect {
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
