package com.kanade.ushio.ui.subject_main.subject_staff

import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.ui.base.IBasePresenter
import com.kanade.ushio.ui.base.IBaseView

interface ISubjectStaffContract {
    interface View : IBaseView {
        fun initData(ep: SubjectEp)

        fun exit()
    }

    interface Presenter : IBasePresenter<View> {
        fun start(subjectEpId: Int)

    }
}