package com.example.androiddevchallenge.data.puppy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PuppiesResponse(
    val animals: List<PuppyResponse>
) {
    @JsonClass(generateAdapter = true)
    data class PuppyResponse(
        val id: Long,
        val breeds: Breed,
        val colors: Colors,
        val age: String,
        val gender: String,
        val size: String,
        val coat: String?,
        val name: String,
        val description: String?,
        val photos: List<Photo>
    ) {
        @JsonClass(generateAdapter = true)
        data class Breed(
            val primary: String?,
            val secondary: String?,
            val mixed: Boolean,
            val unknown: Boolean
        )

        @JsonClass(generateAdapter = true)
        data class Colors(
            val primary: String?,
            val secondary: String?,
            val tertiary: String?
        )

        @JsonClass(generateAdapter = true)
        data class Photo(
            val small: String?,
            val medium: String?,
            val large: String?,
            val full: String?
        )
    }
}
