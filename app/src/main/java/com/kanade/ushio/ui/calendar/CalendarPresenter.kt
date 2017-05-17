package com.kanade.ushio.ui.calendar

import com.blankj.utilcode.util.LogUtils
import com.kanade.ushio.R
import com.kanade.ushio.adapter.models.CalendarSection
import com.kanade.ushio.model.calendar.CalendarModel
import com.kanade.ushio.model.calendar.ICalendarModel
import com.kanade.ushio.ui.base.BasePresenter

class CalendarPresenter : BasePresenter<ICalendarContract.View>(), ICalendarContract.Presenter {
    private lateinit var model: ICalendarModel

    override fun attach(view: ICalendarContract.View) {
        super.attach(view)
        model = CalendarModel()
        model.start()

        val disposable = model.getCalendar()
                .doOnSubscribe { view.enableRefreshing() }
                .doOnComplete { view.disableRefreshing() }
                .subscribe ({ response ->
                    val datas = ArrayList<CalendarSection>()
                    response.forEach { day ->
                        datas.add(CalendarSection(true, day.weekday.cn))
                        datas.addAll(day.items.map { CalendarSection(it) })
                    }
                    view.showDatas(datas) })
                { throwable ->
                    LogUtils.e(TAG, throwable.toString())
                    view.disableRefreshing()
                    view.notice(R.string.no_internet) }
        addDisposable(disposable)
    }

    override fun detach() {
        super.detach()
        model.stop()
    }

    override fun refresh() {
        val disposable = model.getCalendarFromServer()
                .doOnSubscribe { view.enableRefreshing() }
                .doOnComplete { view.disableRefreshing() }
                .subscribe ({ response ->
                    val datas = ArrayList<CalendarSection>()
                    response.forEach { day ->
                        datas.add(CalendarSection(true, day.weekday.cn))
                        datas.addAll(day.items.map { CalendarSection(it) })
                    }
                    view.showDatas(datas)
                    view.notice(R.string.success)})
                { throwable ->
                    LogUtils.e(TAG, throwable.toString())
                    view.disableRefreshing()
                    view.notice(R.string.no_internet) }
        addDisposable(disposable)
    }
}