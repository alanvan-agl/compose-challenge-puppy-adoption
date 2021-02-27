package com.example.androiddevchallenge.data.puppy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class PuppyRepository(private val api: Api): PuppyContract.Repository {
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
