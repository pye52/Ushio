package com.kanade.ushio.arch.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kanade.ushio.api.UserCollectionService
import com.kanade.ushio.arch.repository.UserCollectionRepository
import com.kanade.ushio.arch.room.SmallSubjectDao
import com.kanade.ushio.arch.room.SubjectDao
import com.kanade.ushio.arch.viewmodel.LoginViewModel
import com.kanade.ushio.arch.viewmodel.UserCollectionViewModel

class UserCollectionViewModelFactory(private var service: UserCollectionService,
                                     private var smallSubjectDao: SmallSubjectDao,
                                     private var subjectDao: SubjectDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserCollectionViewModel::class.java)) {
            val repository = UserCollectionRepository(service, smallSubjectDao, subjectDao)
            return UserCollectionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}