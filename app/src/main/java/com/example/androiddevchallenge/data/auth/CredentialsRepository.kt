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
                val credentials = Credentials(
                    it.access_token,
                    it.expires_in
                )
                cachedCredentials = credentials
                credentials
            }
        }

    interface Api {
        @GET("v2/oauth2/token")
        fun get(): Flow<CredentialsResponse>
    }
}
