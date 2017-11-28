package com.kanade.ushio.ui.subject_main.subject_detail

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.kanade.ushio.R
import com.kanade.ushio.api.responses.GradeResponse
import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.model.subject.SubjectModel
import com.kanade.ushio.ui.base.BasePresenter
import com.kanade.ushio.ui.subject_main.SubjectSubActivity
import com.kanade.ushio.utils.*

class SubjectDetailPresenter : BasePresenter<ISubjectDetailContract.View>(), ISubjectDetailContract.Presenter {
    private lateinit var model : SubjectModel
    private lateinit var ep: SubjectEp

    override fun attach(view: ISubjectDetailContract.View) {
        super.attach(view)
        model = SubjectModel()
        model.start()
    }

    override fun detach() {
        super.detach()
        model.stop()
    }

    override fun start(subjectEpId: Int) {
        val disposable = model.getSubject(subjectEpId)
                .doOnSubscribe { view.showProcessDialog(true) }
                .doOnComplete { view.dismissProcessDialog() }
                .subscribe({ it?.let { ep ->
                        this.ep = ep
                        view.initSubjectEp(ep)
                        setBitmap(ep.images.common)
                    } })
                { e ->
                    LogUtils.e(TAG, e.printStackTrace())
                    view.dismissProcessDialog()
                    view.notice(R.string.no_internet)
                }
        addDisposable(disposable)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.subject_refresh -> refreshSubjectEp()
            R.id.label_subject_summary_staff -> view.startAct(SubjectSubActivity.newStaffInstance(context, ep.id))
            R.id.label_subject_crt -> view.startAct(SubjectSubActivity.newCrtInstance(context, ep.id))
            R.id.label_subject_progress -> view.startAct(SubjectSubActivity.newEpInstance(context, ep.id))
            R.id.subject_grade -> {
                val auth = SharePreferenceUtils.getUserAuth()
                val disposable = model.getSubjectEpGrade(ep.id, auth)
                        .compose(RxJavaUtils.IO2MainThread())
                        .subscribe ({ response ->
                            val grade: GradeResponse
                            // 若lasttouch为0，则用户没有对该番剧有任何评论
                            if (response.lasttouch == 0L) {
                                grade = GradeResponse.create()
                            } else {
                                grade = response
                            }
                            view.showGradeDialog(grade)
                        })
                        { e -> LogUtils.e(TAG, e.printStackTrace())
                            view.notice(R.string.no_internet) }
                addDisposable(disposable)
            }
        }
    }

    override fun updateGrade(status: String, rating: Int, comment: String) {
        val auth = SharePreferenceUtils.getUserAuth()
        val disposable = model.updateGrade(ep.id, status, rating, comment, auth)
                .compose(RxJavaUtils.IO2MainThread())
                .doOnSubscribe { view.showProcessDialog(true) }
                .doOnComplete { view.dismissProcessDialog(); }
                .subscribe({ view.notice(R.string.success) })
                { e ->
                    view.dismissProcessDialog()
                    if (!(e is NullPointerException)) {
                        LogUtils.e(TAG, e.toString())
                        view.notice(R.string.no_internet)} }
        addDisposable(disposable)
    }

    private fun refreshSubjectEp() {
        val disposable = model.getSubjectEpFromServer(ep.id)
                .doOnSubscribe { view.showProcessDialog(true) }
                .doOnComplete { view.dismissProcessDialog() }
                .subscribe({ it?.let { ep ->
                    this.ep = ep
                    view.initSubjectEp(ep)
                    view.notice(R.string.subject_refresh_success)
                } })
                { e ->
                    view.dismissProcessDialog()
                    LogUtils.e(TAG, e.toString())
                    view.notice(R.string.no_internet)
                }
        addDisposable(disposable)
    }

    private fun setBitmap(url: String) {
//        ImageLoaderUtil.getBitmap(context, url, { bitmap ->
//            // 根据短边等比缩小图片
//            val dp170 = ConvertUtils.dp2px(170f)
//            val dp150 = ConvertUtils.dp2px(150f)
//            val w: Int
//            val h: Int
//            if (bitmap.width > bitmap.height) {
//                w = dp150
//                h = (w.toFloat() / bitmap.width * bitmap.height).toInt()
//            } else {
//                h = dp170
//                w = (h.toFloat() / bitmap.height * bitmap.width).toInt()
//            }
//            view.setImg(url, w, h)
//
//            val bgw = Math.round(bitmap.width * 0.5f)
//            val bgh = Math.round(bitmap.height * 0.5f)
//            view.setImgBg(url, bgw, bgh)
//        })
    }
}