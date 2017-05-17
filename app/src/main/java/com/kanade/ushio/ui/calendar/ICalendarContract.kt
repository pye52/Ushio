package com.kanade.ushio.ui.calendar

import com.kanade.ushio.adapter.models.CalendarSection
import com.kanade.ushio.ui.base.IBasePresenter
import com.kanade.ushio.ui.base.IBaseView

interface ICalendarContract {
    interface View : IBaseView {
        fun showDatas(datas: List<CalendarSection>)

        fun enableRefreshing()

        fun disableRefreshing()
    }

    interface Presenter : IBasePresenter<View> {
        fun refresh()
    }
}