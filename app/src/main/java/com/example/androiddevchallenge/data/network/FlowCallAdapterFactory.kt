package com.example.androiddevchallenge.data.network

import kotlinx.coroutines.flow.Flow
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Although Retrofit call is more like a one-shot action, having it wrap the result in Flow<T> will
 * help in providing a fluent API for use in our app
 */
class FlowCallAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)

        if (rawType != Flow::class.java) return null
        check(returnType is ParameterizedType) { "Flow return type must be parameterized as Flow<Foo> or Flow<? extends Foo>" }

        val responseType = getParameterUpperBound(0, returnType)
        return if (getRawType(responseType) == Response::class.java) {
            check(responseType is ParameterizedType) { "Response must be parameterized as Response<Foo> or Response<out Foo>" }
            FlowResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else FlowBodyCallAdapter<Any>(responseType)
    }
}
