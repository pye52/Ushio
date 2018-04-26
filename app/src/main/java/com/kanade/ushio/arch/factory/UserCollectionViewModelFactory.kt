package com.kanade.ushio.arch.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kanade.ushio.api.UserCollectionService
import com.kanade.ushio.arch.repository.UserCollectionRepository
import com.kanade.ushio.arch.room.UserCollectionDao
import com.kanade.ushio.arch.room.SubjectDao
import com.kanade.ushio.arch.viewmodel.UserCollectionViewModel

class UserCollectionViewModelFactory(private var service: UserCollectionService,
                                     private var userCollectionDao: UserCollectionDao,
                                     private var subjectDao: SubjectDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserCollectionViewModel::class.java)) {
            val repository = UserCollectionRepository(service, userCollectionDao, subjectDao)
            return UserCollectionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}