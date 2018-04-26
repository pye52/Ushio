package com.kanade.ushio.arch.repository

import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.room.CalendarDao
import com.kanade.ushio.arch.room.CalendarSubjectDao
import com.kanade.ushio.entity.Calendar
import com.kanade.ushio.entity.CalendarSubject
import io.reactivex.Flowable

class CalendarRepository(private var calendarDao: CalendarDao,
                         private var calendarSubjectDao: CalendarSubjectDao) {

    fun queryCalendar(): Flowable<List<Calendar>> {
        return calendarDao.queryAllCalendar()
                .map<List<Calendar>> { list ->
                    // 由于保存的时间戳都是一样的，因此取第一个即可
                    // 若超时了，则清理数据库中所有数据
                    if (list.isEmpty()) {
                        return@map emptyList()
                    }
                    val currentTime = System.currentTimeMillis()
                    // 每3天更新一次
                    if ((currentTime - list[0].updateTime) > 3 * 24 * 60 * 60 * 1000) {
                        deleteAll()
                        return@map emptyList()
                    } else {
                        return@map list
                    }
                }
                .doOnNext { list ->
                    // 填充每日放送的番剧信息
                    AppDatabase.getInstance().beginTransaction()
                    list.forEach { calendar ->
                        calendar.subjects = calendarSubjectDao.queryAllCalendarSubject(calendar.calendarId)
                    }
                    AppDatabase.getInstance().setTransactionSuccessful()
                    AppDatabase.getInstance().endTransaction()
                }
    }

    fun insertCalendar(list: List<Calendar>): ArrayList<Long> {
        // 先保存每日放送下的所有番剧
        val time = System.currentTimeMillis()
        val array = arrayListOf<Long>()
        AppDatabase.getInstance().beginTransaction()
        list.forEachIndexed { index, calendar ->
            calendar.updateTime = time

            // 先保存calendar，以获得数据库的主键值
            array.add(calendarDao.insertCalendar(calendar))
            calendar.subjects?.forEach { subject ->
                subject.calendarId = array[index]
                calendarSubjectDao.insertCalendarSubject(subject)
            }
        }
        AppDatabase.getInstance().setTransactionSuccessful()
        AppDatabase.getInstance().endTransaction()
        return array
    }

    fun updateCalendarSubject(subject: CalendarSubject): Int {
        return calendarSubjectDao.updateCalendarSubject(subject)
    }

    fun deleteAll() {
        calendarSubjectDao.deleteAll()
        calendarDao.deleteAll()
    }
}