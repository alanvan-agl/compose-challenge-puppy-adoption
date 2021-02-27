package com.example.androiddevchallenge.data.puppy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PuppyResponse(
    val animal: Puppy
)
