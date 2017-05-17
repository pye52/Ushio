package com.kanade.ushio.ui.subject_main.subject_progress

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.kanade.ushio.R
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.entity.WatchStatus
import com.kanade.ushio.model.subject_progress.ISubjectProgressModel
import com.kanade.ushio.model.subject_progress.SubjectProgressModel
import com.kanade.ushio.utils.SharePreferenceUtils

class SubjectProgressPresenter : com.kanade.ushio.ui.base.BasePresenter<ISubjectProgressContract.View>(), ISubjectProgressContract.Presenter {
    // 记录正在操作的"集数"状态和位置
    private lateinit var ep: Ep
    private lateinit var model: ISubjectProgressModel
    private var updatePosi: Int = 0

    override fun attach(view: ISubjectProgressContract.View?) {
        super.attach(view)
        model = SubjectProgressModel()
        model.start()
    }

    override fun detach() {
        super.detach()
        model.stop()
    }

    override fun start(subjectEpId: Int) {
        val userId = SharePreferenceUtils.getUserId()
        val auth = SharePreferenceUtils.getUserAuth()
        val disposable = model.getProgress(userId, subjectEpId, auth)
                .subscribe ({ view.initDatas(it) })
                { e ->
                    LogUtils.e(TAG, e.printStackTrace())
                    view.notice(R.string.no_internet) }
        addDisposable(disposable)
    }

    override fun bottomOnClick(v: View) {
        val statusStr: String
        when (v.id) {
            R.id.pw_ep_wish -> statusStr = WatchStatus.WISH_STR
            R.id.pw_ep_watched -> statusStr = WatchStatus.WATCHED_STR
            R.id.pw_ep_drop -> statusStr = WatchStatus.DROP_STR
            else -> statusStr = WatchStatus.UNDO_STR
        }
        val auth = SharePreferenceUtils.getUserAuth()
        val disposable = model.updateProgress(auth, ep.id, statusStr)
                .doOnSubscribe { view.showProcessDialog(true) }
                .doOnComplete { view.dismissBottomDialog(); view.dismissProcessDialog() }
                .subscribe({ view.setEpStatus(updatePosi) })
                { throwable ->
                    LogUtils.e(TAG, throwable.printStackTrace())
                    view.notice(R.string.no_internet)
                    view.dismissBottomDialog()
                    view.dismissProcessDialog() }
        addDisposable(disposable)
    }

    override fun onRvItemClick(ep: Ep, position: Int) {
        this.ep = ep
        this.updatePosi = position
        // 未放送完的剧集不需要操作
        if (ep.isShow) {
            view.showBottomDialog(ep)
        } else {
            view.notice(R.string.no_air)
        }
    }
}