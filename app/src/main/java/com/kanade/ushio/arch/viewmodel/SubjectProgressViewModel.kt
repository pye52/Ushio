package com.kanade.ushio.arch.viewmodel

import android.arch.lifecycle.ViewModel
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.repository.EpRepository
import com.kanade.ushio.arch.repository.SubjectProgressRepository
import com.kanade.ushio.entity.ApiResult
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.entity.WatchStatus
import com.kanade.ushio.utils.getUserId
import io.reactivex.Flowable

class SubjectProgressViewModel(private var repository: SubjectProgressRepository,
                               private var epRepo: EpRepository) : ViewModel() {

    private val subjectRepo by lazy { Injection.provideSubjectRepository() }

    fun queryEps(subjectId: Long): Flowable<List<Ep>> {
        return Flowable.concat(
                epRepo.queryEps(subjectId),
                subjectRepo.querySubjectEps(subjectId))
                .filter { it.isNotEmpty() }
                .take(1)
    }

    fun getProgress(subjectId: Long): Flowable<List<WatchStatus>> {
        val userId = getUserId()
        return repository.getProgress(userId.toString(), subjectId)
    }

    fun updateProgress(epId: Long, statusStr: String): Flowable<ApiResult> {
        return repository.updateProgress(epId, statusStr)
    }

    fun updateAnimationProgress(subjectId: Long, epsId: Long): Flowable<ApiResult> {
        return repository.updateAnimationProgress(subjectId, epsId)
    }
}