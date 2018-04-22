package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.UserCollection
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface SmallSubjectDao {
    @Query("select * from UserCollection")
    fun querySmallSubject(): Flowable<List<UserCollection>>

    @Query("select * from UserCollection where id = :id")
    fun querySmallSubject(id: Int): Maybe<UserCollection>

    @Delete
    fun deleteSmallSubject(subject: UserCollection): Int

    @Insert(onConflict = REPLACE)
    fun insertSmallSubject(subject: UserCollection): Long

    @Insert(onConflict = REPLACE)
    fun insertSmallSubject(list: List<UserCollection>)

    @Update
    fun updateSmallSubject(subject: UserCollection): Int
}