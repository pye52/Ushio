package com.kanade.ushio.api

import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.Injection.provideLoginService
import com.kanade.ushio.utils.*
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route, response: Response): Request? {
        UshioToken?.let { token ->
            val service = provideLoginService()
            val call = service.refreshSync("refresh_token", APP_ID, APP_SECRET, token.refreshToken, REDIRECT_URI)
            val newToken = call.execute().body()
            if (newToken == null) {
                throw TokenNotExist()
            } else {
                // 保存到本地
                AppDatabase.getInstance()
                        .userTokenDao()
                        .updateUserToken(token)
                UshioToken = token
                return response.request()
                        .newBuilder()
                        .header("token", newToken.token)
                        .build()
            }
        }
        throw TokenNotExist()
    }
}