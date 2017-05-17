package com.kanade.ushio.model.subject

import com.kanade.ushio.api.ApiManager
import com.kanade.ushio.api.GradeService
import com.kanade.ushio.api.ResponseGroupService
import com.kanade.ushio.api.UpdateGradeService
import com.kanade.ushio.entity.WatchStatus
import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.utils.RxJavaUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import io.realm.RealmList

class SubjectModel : ISubjectModel {
    private lateinit var realm: Realm
    override fun start() {
        realm = Realm.getDefaultInstance()
    }

    override fun stop() = realm.close()

    override fun getSubject(subjectId: Int): Observable<SubjectEp?> =
            Observable.concat(getSubjectEpFromLocal(subjectId),
                    getSubjectEpFromServer(subjectId))
                    .filter { it != null }
                    .take(1)


    override fun getSubjectEpFromServer(subjectId: Int): Observable<SubjectEp?> =
            ApiManager.getRetrofit()
                    .create(ResponseGroupService::class.java)
                    .getSubjectLarge(subjectId)
                    .map { response ->
                        val realm = Realm.getDefaultInstance()
                        realm.executeTransaction {
                            initEps(response, realm)
                            it.copyToRealmOrUpdate(response)
                        }
                        realm.close()
                    }
                    .compose(RxJavaUtils.IO2MainThread())
                    .flatMap { getSubjectEpFromLocal(subjectId) }

    override fun getSubjectEpFromLocal(subjectId: Int): Observable<SubjectEp?> =
            Observable.just(realm.where(SubjectEp::class.java)
                    .equalTo("id", subjectId)
                    .findAll())
                    .filter { it.isNotEmpty() }
                    .map { it[0] }
                    .subscribeOn(AndroidSchedulers.mainThread())

    override fun getSubjectEpGrade(subjectId: Int, auth: String) =
            ApiManager.getRetrofit()
                    .create(GradeService::class.java)
                    .getSubjectCollection(subjectId, auth)

    override fun updateGrade(subjectId: Int, status: String, rating: Int, comment: String, auth: String) =
            ApiManager.getRetrofit()
                    .create(UpdateGradeService::class.java)
                    .updateCollection(subjectId, status, rating, comment, auth)

    // 初始化剧集状态
    private fun initEps(ep: SubjectEp, realm: Realm) {
        if (ep.eps == null) ep.eps = RealmList()
        ep.eps.forEach {
            val status = WatchStatus.create(it.id)
            realm.copyToRealmOrUpdate(status)
            it.watchStatus = status
        }
    }
}