package com.kanade.ushio.arch.repository;

import com.kanade.ushio.api.CalendarService;
import com.kanade.ushio.entity.Calendar;
import java.util.List;

import io.reactivex.Flowable;

public class CalendarRepository {
    private CalendarService calendarService;

    public CalendarRepository(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    public Flowable<List<Calendar>> queryCalendar() {
        return calendarService.queryCalendar();
    }
}
