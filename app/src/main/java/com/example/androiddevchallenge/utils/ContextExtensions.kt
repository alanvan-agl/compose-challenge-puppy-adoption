package com.example.androiddevchallenge.utils

import android.content.Context
import com.example.androiddevchallenge.PuppyApplication

val Context.singletonInjector get() = (applicationContext as PuppyApplication).singletonInjector