package com.example.androiddevchallenge.ui.screens.model

import com.example.androiddevchallenge.data.puppy.PuppiesResponse

object PuppyMapper {
    fun map(response: PuppiesResponse.PuppyResponse) = Puppy(
        id = response.id,
        name = response.name,
        description = response.description ?: "",
        gender = response.gender,
        size = response.size,
        color = response.colors.primary ?: "",
        photoUrl = response.photos.firstOrNull()?.let {
            mapPhotoUrl(it)
        } ?: ""
    )

    private fun mapPhotoUrl(photo: PuppiesResponse.PuppyResponse.Photo): String? =
        photo.full ?: photo.large ?: photo.medium ?: photo.small
}