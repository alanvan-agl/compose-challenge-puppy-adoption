package com.example.androiddevchallenge

import android.app.Application
import com.example.androiddevchallenge.di.SingletonInjector

class App: Application() {
    val singletonInjector by lazy {
        SingletonInjector(this)
    }
}
