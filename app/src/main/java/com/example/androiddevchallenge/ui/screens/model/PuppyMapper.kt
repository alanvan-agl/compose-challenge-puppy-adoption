package com.example.androiddevchallenge.ui.screens.model

import com.example.androiddevchallenge.data.puppy.Puppy as DataPuppy

object PuppyMapper {
    fun map(response: DataPuppy) = Puppy(
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

    private fun mapPhotoUrl(photo: DataPuppy.Photo): String? =
        photo.full ?: photo.large ?: photo.medium ?: photo.small
}