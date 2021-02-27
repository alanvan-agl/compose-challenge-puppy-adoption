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
