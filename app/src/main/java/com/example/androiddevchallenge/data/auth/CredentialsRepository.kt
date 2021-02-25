package com.example.androiddevchallenge.data.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import retrofit2.http.GET

class CredentialsRepository(private val api: Api) : CredentialsContract.Repository {
    private var cachedCredentials: Credentials? = null

    override fun get(): Flow<Credentials> =
        if (cachedCredentials?.hasNotExpired() == true) {
            flowOf(cachedCredentials!!)
        } else {
            api.get().map {
                cachedCredentials = it
                it
            }
        }

    interface Api {
        @GET("v2/oauth2/token")
        fun get(): Flow<Credentials>
    }
}
