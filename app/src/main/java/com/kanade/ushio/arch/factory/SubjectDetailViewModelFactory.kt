package com.kanade.ushio.arch.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kanade.ushio.api.SubjectService
import com.kanade.ushio.arch.repository.CrtRepository
import com.kanade.ushio.arch.repository.EpRepository
import com.kanade.ushio.arch.repository.SubjectRepository
import com.kanade.ushio.arch.room.SubjectDao
import com.kanade.ushio.arch.viewmodel.SubjectDetailViewModel

class SubjectDetailViewModelFactory(private var service: SubjectService,
                                    private var subjectDao: SubjectDao,
                                    private var crtRepo: CrtRepository,
                                    private var epRepo: EpRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectDetailViewModel::class.java)) {
            val repository = SubjectRepository(service, subjectDao, crtRepo, epRepo)
            return SubjectDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}