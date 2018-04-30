package com.kanade.ushio.arch.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kanade.ushio.api.SubjectProgressService
import com.kanade.ushio.arch.repository.EpRepository
import com.kanade.ushio.arch.repository.SubjectProgressRepository
import com.kanade.ushio.arch.repository.UserTokenRepository
import com.kanade.ushio.arch.room.UserTokenDao
import com.kanade.ushio.arch.viewmodel.SubjectProgressViewModel

class SubjectProgressViewModelFactory(private var subjectRepo: SubjectProgressRepository,
                                      private var epRepo: EpRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectProgressViewModel::class.java)) {
            return SubjectProgressViewModel(subjectRepo, epRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}