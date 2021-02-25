package com.example.androiddevchallenge.utils

fun <T> tryOrNull(action: () -> T?): T? = try {
    action.invoke()
} catch (e: Exception) {
    null
}