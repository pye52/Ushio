package com.kanade.ushio.ui.subject_main.subject_staff

import com.kanade.ushio.model.subject.ISubjectModel
import com.kanade.ushio.model.subject.SubjectModel
import com.kanade.ushio.ui.base.BasePresenter

class SubjectStaffPresenter : BasePresenter<ISubjectStaffContract.View>(), ISubjectStaffContract.Presenter {
    private lateinit var model: ISubjectModel

    override fun attach(view: ISubjectStaffContract.View?) {
        super.attach(view)
        model = SubjectModel()
        model.start()
    }

    override fun detach() {
        super.detach()
        model.stop()
    }

    override fun start(subjectEpId: Int) {
        val disposable = model.getSubjectEpFromLocal(subjectEpId)
                .subscribe { it?.let { view.initData(it) } ?: view.exit() }
        addDisposable(disposable)
    }
}