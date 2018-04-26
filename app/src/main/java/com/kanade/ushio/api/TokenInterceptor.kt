package com.kanade.ushio.api

import com.google.gson.JsonParser
import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.utils.APP_ID
import com.kanade.ushio.utils.APP_SECRET
import com.kanade.ushio.utils.REDIRECT_URI
import com.kanade.ushio.utils.UshioToken
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.nio.charset.Charset

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val refreshToken = UshioToken?.refreshToken ?: throw TokenNotExist()
        val bodyString = copyString(response.body())
        if (bodyString.isNotEmpty() && bodyString[0] == '{') {
            val jsonObj = JsonParser().parse(bodyString).asJsonObject
            if (!jsonObj.has("code")) {
                return response
            }
            val code = jsonObj.get("code").asInt
            if (code == 401) {
                // 刷新token(同步的方式)
                val service = Injection.provideLoginService()
                val call = service.refreshSync("refresh_token", APP_ID, APP_SECRET, refreshToken, REDIRECT_URI)
                val token = call.execute().body() ?: throw TokenNotExist()
                // 保存到本地
                AppDatabase.getInstance()
                        .userTokenDao()
                        .updateUserToken(token)
                UshioToken = token
                // 更新请求的token
                val newRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer ${token.token}")
                        .build()
                return chain.proceed(newRequest)
            }
        }
        return response
    }

    private fun copyString(body: ResponseBody?): String {
        if (body == null) {
            return ""
        }
        val source = body.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()
        var charset = Charset.forName("UTF-8")
        val contentType = body.contentType()
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"))
        }
        return buffer.clone().readString(charset)
    }
}