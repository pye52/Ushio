package com.kanade.ushio.arch.viewmodel

import android.arch.lifecycle.ViewModel
import com.kanade.ushio.api.AuthService
import com.kanade.ushio.arch.repository.UserTokenRepository
import com.kanade.ushio.entity.UserToken
import com.kanade.ushio.utils.*
import io.reactivex.Flowable

class LoginViewModel(private val service: AuthService, private var repository: UserTokenRepository) : ViewModel() {
    fun queryUserToken(): Flowable<UserToken> {
        val userId = getUserId()
        return repository.queryUserToken(userId)
    }

    // TODO 未考虑code超时及错误的情况
    fun login(code: String): Flowable<UserToken> {
        return service.auth("authorization_code", APP_ID, APP_SECRET, code, REDIRECT_URI)
                .doOnNext { token ->
                    if (token != null) {
                        // 保存到本地
                        repository.insertUserToken(token)
                        // 保存用户id和登录时间
                        saveUserToken(token.token)
                        saveUserId(token.userId)
                        saveLastLoginTime(System.currentTimeMillis())
                    }
                }
    }

    fun refresh(refreshToken: String): Flowable<UserToken> {
        return service.refresh("refresh_token", APP_ID, APP_SECRET, refreshToken, REDIRECT_URI)
                .doOnNext { token ->
                    if (token != null) {
                        // 更新本地的数据
                        repository.updateUserToken(token)
                        saveUserToken(token.token)
                        saveLastLoginTime(System.currentTimeMillis())
                    }
                }
    }
}