package com.kanade.ushio.utils

import com.blankj.utilcode.util.SPUtils

const val SP_NAME = "ushio"

private const val USER_TOKEN = "user_token"
private const val USER_ID = "user_id"
private const val LOGIN_TIME = "login_time"

fun saveUserToken(token: String) {
    SPUtils.getInstance(SP_NAME)
            .put(USER_TOKEN, token)
}

fun getUserToken(): String {
    return SPUtils.getInstance(SP_NAME)
            .getString(USER_TOKEN, "")
}

fun saveUserId(userId: Long) {
    SPUtils.getInstance(SP_NAME)
            .put(USER_ID, userId)
}

fun getUserId(): Long {
    return SPUtils.getInstance(SP_NAME)
            .getLong(USER_ID, -1)
}

fun saveLastLoginTime(time: Long) {
    SPUtils.getInstance(SP_NAME)
            .put(LOGIN_TIME, time)
}

fun getLastLoginTime(): Long {
    return SPUtils.getInstance(SP_NAME)
            .getLong(LOGIN_TIME, -1)
}