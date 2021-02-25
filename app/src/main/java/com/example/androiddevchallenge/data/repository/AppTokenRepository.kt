package com.example.androiddevchallenge.data.repository

import android.os.CountDownTimer
import com.example.androiddevchallenge.data.auth.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.http.GET

class AppTokenRepository(private val api: Api): TokenRepository {

    init {
        fetchToken()
    }

    private var token: String = ""
    private var countDownTimer: CountDownTimer? = null
    private var coroutineScope: CoroutineScope? = null

    private fun fetchToken() {
        coroutineScope?.cancel()
        coroutineScope = CoroutineScope(Dispatchers.IO).apply {
            launch {
                api.get().map {
                    countDownTimer = object: CountDownTimer(it.expiresIn, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            if (millisUntilFinished < 60_000) {
                                fetchToken()
                                countDownTimer?.cancel()
                            }
                        }
                        override fun onFinish() { }
                    }.start()
                    it
                }.collect {
                    token = it.value
                }
            }
        }
    }

    override fun getToken(): String = token

    interface Api {
        @GET("v2/oauth2/token")
        fun get(): Flow<Auth>
    }
}
