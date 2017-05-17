package com.kanade.ushio.ui.subject_main.subject_progress

import com.kanade.ushio.entity.Ep

interface ISubjectProgressContract {
    interface View : com.kanade.ushio.ui.base.IBaseView {
        fun initDatas(datas: List<Ep>)

        fun showBottomDialog(ep: Ep)

        fun dismissBottomDialog()

        fun setEpStatus(position: Int)
    }

    interface Presenter : com.kanade.ushio.ui.base.IBasePresenter<View> {
        fun start(subjectEpId: Int)

        fun bottomOnClick(v: android.view.View)

        fun onRvItemClick(ep: Ep, position: Int)
    }
}