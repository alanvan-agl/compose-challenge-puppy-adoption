package com.example.androiddevchallenge.data.puppy

import kotlinx.coroutines.flow.Flow

object PuppyContract {
    interface Repository {
        fun getAllPuppies(): Flow<List<Puppy>>
        fun getPuppy(puppyId: Long): Flow<Puppy>
    }
}
