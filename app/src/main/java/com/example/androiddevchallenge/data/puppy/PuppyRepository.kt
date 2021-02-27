package com.example.androiddevchallenge.data.puppy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.http.GET
import retrofit2.http.Query

class PuppyRepository(private val api: Api): PuppyContract.Repository {
    override fun getAllPuppies(): Flow<List<PuppiesResponse.PuppyResponse>> {
        return api.getAllPuppies("Dog").map { it.animals }
    }

    interface Api {
        @GET("v2/animals")
        fun getAllPuppies(@Query("type") type: String): Flow<PuppiesResponse>
    }
}
