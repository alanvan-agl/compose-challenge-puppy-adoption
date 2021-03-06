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
package com.example.androiddevchallenge.data.puppy

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Puppy(
    val id: Long,
    val breeds: Breed,
    val colors: Colors,
    val age: String,
    val gender: String,
    val size: String,
    val coat: String?,
    val name: String,
    val description: String?,
    val photos: List<Photo>,
    val contact: Contact
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

    @JsonClass(generateAdapter = true)
    data class Contact(
        val email: String?,
        val phone: String?
    )
}
