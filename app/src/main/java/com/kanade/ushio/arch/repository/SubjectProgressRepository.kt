package com.kanade.ushio.arch.repository

import com.kanade.ushio.api.SubjectProgressService
import com.kanade.ushio.entity.ApiResult
import com.kanade.ushio.entity.WatchStatus
import io.reactivex.Flowable

class SubjectProgressRepository(private var service: SubjectProgressService) {
    fun getProgress(username: String, subjectId: Long): Flowable<List<WatchStatus>> {
        return service.getProgress(username, subjectId)
                .map { it.eps }
    }

    fun updateProgress(epId: Long, statusStr: String): Flowable<ApiResult> {
        return service.updateProgress(epId, statusStr)
    }

    fun updateAnimationProgress(subjectId: Long, epsId: Long): Flowable<ApiResult> {
        return service.updateAnimationProgress(subjectId, epsId)
    }
}