package com.kanade.ushio.ui.subject_main.subject_crt

import com.kanade.ushio.entity.subject.Crt
import com.kanade.ushio.ui.base.IBasePresenter
import com.kanade.ushio.ui.base.IBaseView

interface ISubjectCrtContract {
    interface View : IBaseView {
        fun initDatas(datas: List<Crt>)
    }

    interface Presenter : IBasePresenter<View> {
        fun start(subjectEpId: Int)
    }
}