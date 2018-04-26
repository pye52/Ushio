package com.kanade.ushio.utils

import com.blankj.utilcode.util.SPUtils

const val SP_NAME = "ushio"

private const val USER_ID = "user_id"

fun saveUserId(userId: Long) {
    SPUtils.getInstance(SP_NAME)
            .put(USER_ID, userId)
}

fun getUserId(): Long {
    return SPUtils.getInstance(SP_NAME)
            .getLong(USER_ID, -1)
}