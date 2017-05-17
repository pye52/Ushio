package com.kanade.ushio.ui.about

import com.kanade.ushio.ui.base.IBasePresenter
import com.kanade.ushio.ui.base.IBaseView

interface IAboutContract {
    interface View : IBaseView {

    }

    interface Presenter : IBasePresenter<View> {
        fun update()
    }
}