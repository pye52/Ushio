package com.kanade.ushio.arch.repository

import com.kanade.ushio.arch.room.UserTokenDao
import com.kanade.ushio.entity.UserToken
import io.reactivex.Flowable

class UserTokenRepository(private val dao: UserTokenDao) {
    fun queryUserToken(userId: Long): Flowable<UserToken> {
        return dao.queryUserToken(userId)
    }

    fun insertUserToken(token: UserToken): Long {
        return dao.insertUserToken(token)
    }

    fun updateUserToken(token: UserToken): Int {
        return dao.updateUserToken(token)
    }
}