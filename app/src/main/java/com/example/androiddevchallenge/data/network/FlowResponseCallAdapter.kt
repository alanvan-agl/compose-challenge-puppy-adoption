package com.example.androiddevchallenge.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowResponseCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Flow<Response<T>>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Flow<Response<T>> = flow {
        emit(
            suspendCancellableCoroutine<Response<T>> { cont ->
                val realCall = call.clone()
                realCall.enqueue(object : Callback<T> {
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        cont.resumeWithException(t)
                    }

                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        cont.resume(response)
                    }
                })
                cont.invokeOnCancellation { realCall.cancel() }
            }
        )
    }
}