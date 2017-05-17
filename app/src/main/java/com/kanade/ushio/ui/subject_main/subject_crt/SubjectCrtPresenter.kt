package com.kanade.ushio.ui.subject_main.subject_crt

import com.kanade.ushio.R
import com.kanade.ushio.model.subject_crt.ISubjectCrtModel
import com.kanade.ushio.model.subject_crt.SubjectCrtModel
import com.kanade.ushio.ui.base.BasePresenter

class SubjectCrtPresenter : BasePresenter<ISubjectCrtContract.View>(), ISubjectCrtContract.Presenter {
    private lateinit var model: ISubjectCrtModel

    override fun attach(view: ISubjectCrtContract.View?) {
        super.attach(view)
        model = SubjectCrtModel()
        model.start()
    }

    override fun detach() {
        super.detach()
        model.stop()
    }

    override fun start(subjectEpId: Int) {
        val disposable = model.getSubjectCrt(subjectEpId)
                .subscribe ({ view.initDatas(it) })
                { view.notice(R.string.no_internet) }
        addDisposable(disposable)
    }
}