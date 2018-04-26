package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.UserCollection
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface UserCollectionDao {
    @Query("select * from UserCollection")
    fun queryUserCollection(): Flowable<List<UserCollection>>

    @Query("select * from UserCollection where id = :id")
    fun queryUserCollection(id: Int): Maybe<UserCollection>

    @Delete
    fun deleteUserCollection(collection: UserCollection): Int

    @Insert(onConflict = REPLACE)
    fun insertUserCollection(collection: UserCollection): Long

    @Insert(onConflict = REPLACE)
    fun insertUserCollection(list: List<UserCollection>)

    @Update
    fun updateUserCollection(collection: UserCollection): Int
}