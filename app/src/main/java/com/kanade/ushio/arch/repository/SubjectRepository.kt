package com.kanade.ushio.arch.repository

import com.kanade.ushio.api.SubjectService
import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.room.SubjectDao
import com.kanade.ushio.entity.*
import io.reactivex.Flowable

class SubjectRepository(private var service: SubjectService,
                        private var subjectDao: SubjectDao,
                        private var crtRepo: CrtRepository,
                        private var epRepo: EpRepository) {
    /**
     * 向服务器请求指定id的番剧(含详细信息如剧集，制作人，角色等)
     */
    fun querySubjectFromServer(subjectId: Long): Flowable<LargeSubject> {
        return service.querySubject(subjectId)
                .doOnNext {
                    AppDatabase.getInstance().beginTransaction()
                    it.crt?.forEach {
                        crtRepo.insertCrt(it)
                    }
                    it.eps?.let { eps ->
                        eps.forEach { ep ->
                            ep.subjectId = it.id
                        }
                        epRepo.insertEps(eps)
                    }
                    AppDatabase.getInstance().setTransactionSuccessful()
                    AppDatabase.getInstance().endTransaction()
                }
    }

    /**
     * 先检查本地数据库是否存在该subject数据
     *
     * 若存在，则覆盖但保持其hold及onAir值
     */
    fun insertSubject(subject: Subject) {
        val t = subjectDao.querySubjectSync(subject.id)
        if (t == null) {
            subjectDao.insertSubject(subject)
        } else {
            subject.hold = t.hold
            subjectDao.updateSubject(subject)
        }
    }

    /**
     * 获取指定番剧的收藏状态
     */
    fun querySubjectCollection(subjectId: Long): Flowable<SubjectCollection> {
        return service.querySubjectCollection(subjectId)
    }

    /**
     * 向服务器请求更新该番剧的评论状态
     */
    fun updateSubject(subjectId: Long, status: String, comment: String, tags: String, rating: Int, privacy: Int): Flowable<SubjectCollection> {
        return service.updateSubject(subjectId, status, comment, tags, rating, privacy)
    }
}