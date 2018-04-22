package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.UserToken
import io.reactivex.Flowable

@Dao
interface UserTokenDao {
    @Query("select * from usertoken where userId = :userId")
    fun queryUserToken(userId: Long): Flowable<UserToken>

    @Insert(onConflict = REPLACE)
    fun insertUserToken(token: UserToken): Long

    @Delete
    fun deleteUserToken(token: UserToken): Int

    @Update
    fun updateUserToken(token: UserToken): Int
}