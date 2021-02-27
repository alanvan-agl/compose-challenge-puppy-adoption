package com.example.androiddevchallenge.data.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CredentialsResponse(
    val expires_in: Long,
    val access_token: String
)
