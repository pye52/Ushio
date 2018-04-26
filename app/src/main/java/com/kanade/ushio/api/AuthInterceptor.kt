package com.kanade.ushio.api

import com.kanade.ushio.utils.UshioToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        UshioToken?.let { token ->
            val original = chain.request()
            val builder = original.newBuilder()
            builder.addHeader("Authorization", "Bearer ${token.token}")
            return chain.proceed(builder.build())
        }
        return chain.proceed(chain.request())
    }
}