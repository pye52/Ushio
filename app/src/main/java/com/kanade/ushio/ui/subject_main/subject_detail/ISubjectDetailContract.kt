package com.kanade.ushio.ui.subject_main.subject_detail

import android.content.Intent
import com.kanade.ushio.api.responses.GradeResponse
import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.ui.base.IBasePresenter
import com.kanade.ushio.ui.base.IBaseView

interface ISubjectDetailContract {
    interface View : IBaseView {
        fun initSubjectEp(ep: SubjectEp)

        fun startAct(intent: Intent)

        fun setImgBg(url: String, width: Int, height: Int)

        fun setImg(url: String, width: Int, height: Int)

        fun showGradeDialog(response: GradeResponse)
    }

    interface Presenter : IBasePresenter<View> {
        fun start(subjectEpId: Int)

        fun onClick(v: android.view.View)

        fun updateGrade(status: String, rating: Int, comment: String)
    }
}