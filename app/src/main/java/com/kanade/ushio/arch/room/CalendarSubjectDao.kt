package com.kanade.ushio.arch.room

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.kanade.ushio.entity.CalendarSubject

@Dao
interface CalendarSubjectDao {
    @Query("select * from calendarsubject where calendarId = :calendarId")
    fun queryAllCalendarSubject(calendarId: Long): List<CalendarSubject>

    @Insert(onConflict = REPLACE)
    fun insertCalendarSubject(calendarSubject: CalendarSubject): Long

    @Update
    fun updateCalendarSubject(calendarSubject: CalendarSubject): Int

    @Delete
    fun deleteCalendarSubject(calendarSubject: CalendarSubject): Int

    @Query("delete from calendarsubject")
    fun deleteAll()
}