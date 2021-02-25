package com.example.androiddevchallenge.data.auth

import kotlinx.coroutines.flow.Flow

object CredentialsContract {
    interface Repository {
        fun get(): Flow<Credentials>
    }
}