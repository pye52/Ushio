package com.kanade.ushio.api

import com.kanade.ushio.entity.Calendar
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET

interface CalendarService {
    @GET("/calendar")
    fun queryCalendar(): Flowable<List<Calendar>>

    @GET("/calendar")
    fun queryCalendarSync(): Call<List<Calendar>>
}