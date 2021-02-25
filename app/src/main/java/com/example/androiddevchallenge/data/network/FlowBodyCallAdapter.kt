package com.example.androiddevchallenge.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Suppress("UNCHECKED_CAST")
class FlowBodyCallAdapter<T>(private val responseType: Type) : CallAdapter<T, Flow<T>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Flow<T> = flow {
        emit(
            suspendCancellableCoroutine<T> { cont ->
                val realCall = call.clone()
                realCall.enqueue(object : Callback<T> {
                    override fun onFailure(call: Call<T>, t: Throwable) {
                        cont.resumeWithException(t)
                    }

                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        try {
                            if (response.isSuccessful) {
                                cont.resume(response.body() as T)
                            } else {
                                cont.resumeWithException(HttpException(response))
                            }
                        } catch (e: Exception) {
                            cont.resumeWithException(e)
                        }
                    }
                })
                cont.invokeOnCancellation { realCall.cancel() }
            }
        )
    }
}
