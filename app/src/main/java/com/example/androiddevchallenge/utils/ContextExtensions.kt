package com.example.androiddevchallenge.utils

import android.content.Context
import com.example.androiddevchallenge.App

val Context.singletonInjector get() = (applicationContext as App).singletonInjector