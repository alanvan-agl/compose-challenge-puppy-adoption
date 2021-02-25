package com.example.androiddevchallenge.data.puppy

data class PuppyResponse(
    val breed: Breed,
    val colors: Colors,
    val age: String,
    val gender: String,
    val size: String,
    val coat: String,
    val name: String,
    val description: String,
    val photos: List<Photo>
) {
    data class Breed(
        val primary: String?,
        val secondary: String?,
        val mixed: Boolean,
        val unknown: Boolean
    )

    data class Colors(
        val primary: String?,
        val secondary: String?,
        val tertiary: String?
    )

    data class Photo(
        val small: String,
        val medium: String,
        val large: String,
        val full: String
    )
}
