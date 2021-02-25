package com.example.androiddevchallenge.data.puppy

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

class PuppyRepository(private val api: Api): PuppyContract.Repository {
    override fun getAllPuppies(): Flow<List<PuppyResponse>> {
        return api.getAllPuppies("Dog")
    }

    interface Api {
        @GET("v2/animals")
        fun getAllPuppies(@Query("type") type: String): Flow<List<PuppyResponse>>
    }
}
