package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.Calendar
import io.reactivex.Flowable

@Dao
interface CalendarDao {
    @Query("select * from calendar")
    fun queryAllCalendar(): Flowable<List<Calendar>>

    @Insert(onConflict = REPLACE)
    fun insertCalendar(calendar: Calendar): Long

    @Update
    fun updateCalendar(calendar: Calendar): Int

    @Delete
    fun deleteCalendar(calendar: Calendar): Int

    @Query("delete from calendar")
    fun deleteAll()
}