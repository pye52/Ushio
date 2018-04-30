package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import com.kanade.ushio.entity.Subject
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface SubjectDao {
    @Query("select * from subject where id = :id")
    fun querySubject(id: Long): Maybe<Subject>

    @Delete
    fun deleteSubject(subject: Subject): Int

    @Query("select * from subject where id = :id")
    fun querySubjectSync(id: Long): Subject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubject(subject: Subject): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubject(list: List<Subject>)

    @Update
    fun updateSubject(subject: Subject): Int
}