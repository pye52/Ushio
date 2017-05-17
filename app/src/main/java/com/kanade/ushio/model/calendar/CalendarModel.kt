package com.kanade.ushio.model.calendar

import com.kanade.ushio.api.ApiManager
import com.kanade.ushio.api.CalendarService
import com.kanade.ushio.entity.Calendar
import com.kanade.ushio.utils.RxJavaUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm

class CalendarModel : ICalendarModel {
    private lateinit var realm: Realm

    override fun start() {
        realm = Realm.getDefaultInstance()
    }

    override fun stop() = realm.close()

    override fun getCalendar(): Observable<List<Calendar>> =
            Observable.concat(getCalendarFromLocal(),
                    getCalendarFromServer())
                    .filter { it.isNotEmpty() }
                    .take(1)

    override fun getCalendarFromLocal(): Observable<List<Calendar>> =
            Observable.just(realm.where(Calendar::class.java)
                    .findAll())
                    .map { it.toList() }
                    .subscribeOn(AndroidSchedulers.mainThread())

    override fun getCalendarFromServer(): Observable<List<Calendar>> =
            ApiManager.getRetrofit()
                    .create(CalendarService::class.java)
                    .get()
                    .compose(RxJavaUtils.IO2MainThread())
                    .doOnNext { response -> realm.executeTransaction { it.copyToRealmOrUpdate(response) } }

    override fun clear() =
            realm.executeTransaction { it.delete(Calendar::class.java) }
}