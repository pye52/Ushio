package com.kanade.ushio.model.subject_progress

import com.kanade.ushio.api.ApiManager
import com.kanade.ushio.api.ProgressService
import com.kanade.ushio.api.UpdateProgressService
import com.kanade.ushio.api.responses.ProgressResponse
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.entity.WatchStatus
import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.model.subject.ISubjectModel
import com.kanade.ushio.model.subject.SubjectModel
import com.kanade.ushio.utils.RxJavaUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.realm.Realm
import java.util.*

class SubjectProgressModel : ISubjectProgressModel {
    private lateinit var realm: Realm
    private lateinit var epModel: ISubjectModel

    override fun start() {
        realm = Realm.getDefaultInstance()
        epModel = SubjectModel()
        epModel.start()
    }

    override fun stop() {
        realm.close()
        epModel.stop()
    }

    /**
     * 先从本地数据库根据[subjectEpId]获取[SubjectEp]实体
     *
     * 再请求服务器获取进度记录，根据剧集size填充进度
     *
     * 由于涉及realm操作，请不要切换线程
     *
     * @return 返回进度记录，注意当用户没有对该番剧的剧集有过任何历史操作，服务器会返回为null，此时进度皆为[WatchStatus.INIT]
     */
    override fun getProgress(userId: Int, subjectEpId: Int, auth: String) =
            Observable.zip(getSubjectEpFromLocal(subjectEpId),
                    getProgressFromServer(userId, subjectEpId, auth),
                    BiFunction<List<Ep>, ProgressResponse, List<Ep>> { eps, response ->
                        realm.beginTransaction()
                        addEps2SubjectEp(eps, response.eps)
                        realm.commitTransaction()
                        eps
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())

    override fun updateProgress(auth: String, epId: Int, statusStr: String) =
            ApiManager.getRetrofit()
                    .create(UpdateProgressService::class.java)
                    .updateEp(auth, epId, statusStr)
                    .compose(RxJavaUtils.IO2MainThread())
                    .filter { it.code == 200 }
                    .doOnNext {
                        realm.beginTransaction()
                        realm.where(WatchStatus::class.java)
                                .equalTo("epId", epId)
                                .findFirst()
                                ?.setStatus(statusStr)
                        realm.commitTransaction()
                    }

    private fun getProgressFromServer(userId: Int, epId: Int, auth: String) =
            ApiManager.getRetrofit()
                    .create(ProgressService::class.java)
                    .getSubjectProgress(userId, auth, epId)
                    .compose(RxJavaUtils.IO2MainThread())
                    // 网络错误或者json解析错误，返回一组空串
                    .onErrorReturn { ProgressResponse.create() }

    private fun getSubjectEpFromLocal(subjectEpId: Int) =
            epModel.getSubjectEpFromLocal(subjectEpId)
                    .map { it.eps ?: Collections.emptyList<Ep>() }

    /**
     * 将剧集观看信息填充到[SubjectEp.eps]的[Ep.watchStatus]中
     */
    private fun addEps2SubjectEp(eps: List<Ep>, wrs: List<ProgressResponse.WatchResponse>) {
        if (wrs.isEmpty()) return
        var n = 0
        // 正序排序
        val teps = wrs.sortedBy { it.id }
        var temEp = teps[n]
        run out@ {
            eps.forEach { ep ->
                if (ep.id == temEp.id) {
                    temEp.status.epId = ep.id
                    ep.watchStatus = realm.copyToRealmOrUpdate(temEp.status)
                    if (++n >= teps.size) return@out
                    temEp = teps[n]
                }
            }
        }
    }
}