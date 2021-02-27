/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
