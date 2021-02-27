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
package com.example.androiddevchallenge.data.puppy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class PuppyRepository(private val api: Api) : PuppyContract.Repository {
    override fun getAllPuppies(): Flow<List<Puppy>> =
        api.getAllPuppies("Dog").map { it.animals }

    override fun getPuppy(puppyId: Long): Flow<Puppy> =
        api.getPuppy(puppyId).map { it.animal }

    interface Api {
        @GET("v2/animals")
        fun getAllPuppies(@Query("type") type: String): Flow<PuppiesResponse>

        @GET("v2/animals/{id}")
        fun getPuppy(@Path("id") id: Long): Flow<PuppyResponse>
    }
}
