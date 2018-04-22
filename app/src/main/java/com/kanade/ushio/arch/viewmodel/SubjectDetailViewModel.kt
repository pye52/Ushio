package com.kanade.ushio.arch.viewmodel

import android.arch.lifecycle.ViewModel
import com.kanade.ushio.arch.repository.SubjectRepository
import com.kanade.ushio.entity.LargeSubject
import com.kanade.ushio.entity.SubjectCollection
import io.reactivex.Flowable

class SubjectDetailViewModel(private var repository: SubjectRepository) : ViewModel() {
    fun querySubject(id: Long): Flowable<LargeSubject> {
        return repository.querySubject(id)
    }

    fun querySubjectCollection(subjectId: Long): Flowable<SubjectCollection> {
        return repository.querySubjectCollection(subjectId)
    }

    fun updateSubject(subjectId: Long, status: String, comment: String, tags: String, rating: Int, privacy: Int): Flowable<SubjectCollection> {
        return repository.updateSubject(subjectId, status, comment, tags, rating, privacy)
    }
}