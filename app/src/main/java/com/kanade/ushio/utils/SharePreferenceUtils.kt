package com.kanade.ushio.utils

import com.blankj.utilcode.util.SPUtils
import com.kanade.ushio.entity.User
import com.kanade.ushio.entity.subject.Image

object SharePreferenceUtils {
    private const val IS_LOGINED = "is_logined"

    private var spUtils = SPUtils("share_data")

    fun getSpUtils(): SPUtils = spUtils

    fun setIsLogined(isLogined: Boolean) = spUtils.put(IS_LOGINED, isLogined)
    fun isLogin(): Boolean = spUtils.getBoolean(IS_LOGINED)

    // ---------------------------------- begin: UserSession ---------------------------------
    private const val USER_ID = "user_id"
    private const val USER_URL = "user_url"
    private const val USER_NAME = "user_name"
    private const val USER_NICKNAME = "user_nickname"
    private const val LARGE_AVATAR = "large_avatar"
    private const val MEDIUM_AVATAR = "medium_avatar"
    private const val SMALL_AVATAR = "small_avatar"
    private const val SIGN = "sign"
    private const val AUTH = "auth"
    private const val AUTH_ENCODE = "auth_encode"

    fun getUserId(): Int = spUtils.getInt(USER_ID, -1)
    fun getUserName(): String = spUtils.getString(USER_NAME)
    fun getUserAuth(): String = spUtils.getString(AUTH)
    fun getUserAuthEncode(): String = spUtils.getString(AUTH_ENCODE)
    fun getUserImage(): Image {
        val img = Image()
        img.large = spUtils.getString(LARGE_AVATAR)
        img.medium = spUtils.getString(MEDIUM_AVATAR)
        img.small = spUtils.getString(SMALL_AVATAR)
        return img
    }

    fun saveUserToSp(user: User) {
        spUtils.put(USER_ID, user.id)
        spUtils.put(USER_URL, user.url)
        spUtils.put(USER_NAME, user.username)
        spUtils.put(USER_NICKNAME, user.nickname)
        spUtils.put(SIGN, user.sign)
        spUtils.put(AUTH, user.auth)
        spUtils.put(AUTH_ENCODE, user.authEncode)
        saveUserAvatar(user.avatar)
    }

    private fun saveUserAvatar(image: Image) {
        spUtils.put(LARGE_AVATAR, image.large)
        spUtils.put(MEDIUM_AVATAR, image.medium)
        spUtils.put(SMALL_AVATAR, image.small)
    }
    // ---------------------------------- end: UserSession ---------------------------------
}