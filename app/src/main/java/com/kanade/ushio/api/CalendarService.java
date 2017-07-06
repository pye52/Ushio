package com.kanade.ushio.api;

import com.kanade.ushio.entity.Calendar;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CalendarService {
    @GET("/calendar")
    Observable<List<Calendar>> get();
}
