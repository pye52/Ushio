package com.kanade.ushio.arch.repository

import com.kanade.ushio.api.SubjectService
import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.room.EpDao
import com.kanade.ushio.entity.Ep
import io.reactivex.Flowable

class EpRepository(private var epDao: EpDao,
                   private var service: SubjectService) {
    /*
     * 从数据库获取指定id的番剧的所有剧集
     */
    fun queryEps(subjectId: Long): Flowable<List<Ep>> {
        return epDao.queryEps(subjectId)
                .flatMap { eps ->
                    if (eps.isEmpty()) {
                        return@flatMap queryEpsFromServer(subjectId)
                    } else {
                        return@flatMap Flowable.just(eps)
                    }
                }
    }

    /**
     * 向服务器请求指定id的番剧所有剧集
     */
    private fun queryEpsFromServer(subjectId: Long): Flowable<List<Ep>> {
        return service.querySubjectEps(subjectId)
                .doOnNext {
                    AppDatabase.getInstance().beginTransaction()
                    it.eps?.let { eps ->
                        eps.forEach { ep ->
                            ep.subjectId = it.id
                        }
                        insertEps(eps)
                    }
                    AppDatabase.getInstance().setTransactionSuccessful()
                    AppDatabase.getInstance().endTransaction()
                }
                .map { it.eps }
    }

    fun insertEps(list: List<Ep>): Array<Long> {
        return epDao.insertEps(list)
    }

    fun updateEp(ep: Ep): Int {
        return epDao.updateEp(ep)
    }

    fun deleteEp(ep: Ep): Int {
        return epDao.deleteEp(ep)
    }

}