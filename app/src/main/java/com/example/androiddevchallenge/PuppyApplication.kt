package com.example.androiddevchallenge

import android.app.Application
import com.example.androiddevchallenge.di.SingletonInjector

class PuppyApplication: Application() {
    val singletonInjector by lazy { SingletonInjector(this) }
}
