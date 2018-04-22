package com.kanade.ushio.api

import com.kanade.ushio.utils.getUserToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getUserToken()
        if (token.isNotBlank()) {
            val original = chain.request()
            val builder = original.newBuilder()
            builder.addHeader("Authorization", "Bearer $token")
            return chain.proceed(builder.build())
        }
        return chain.proceed(chain.request())
    }
}