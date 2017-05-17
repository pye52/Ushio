package com.kanade.ushio.model.calendar

import com.kanade.ushio.entity.Calendar
import com.kanade.ushio.model.IBaseModel
import io.reactivex.Observable

interface ICalendarModel : IBaseModel {
    fun getCalendar(): Observable<List<Calendar>>

    fun getCalendarFromLocal(): Observable<List<Calendar>>

    fun getCalendarFromServer(): Observable<List<Calendar>>

    fun clear()
}