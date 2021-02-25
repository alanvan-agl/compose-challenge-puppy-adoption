package com.example.androiddevchallenge.data.auth

data class Credentials(
    val value: String,
    val expiresAt: Long
) {
    fun hasNotExpired() = System.currentTimeMillis() < expiresAt
}
