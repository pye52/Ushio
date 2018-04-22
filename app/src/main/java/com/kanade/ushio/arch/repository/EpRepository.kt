package com.kanade.ushio.arch.repository

import com.kanade.ushio.arch.room.EpDao
import com.kanade.ushio.entity.Ep
import io.reactivex.Flowable

class EpRepository(private var epDao: EpDao) {
    fun queryEps(subjectId: Long): Flowable<List<Ep>> {
        return epDao.queryEps(subjectId)
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