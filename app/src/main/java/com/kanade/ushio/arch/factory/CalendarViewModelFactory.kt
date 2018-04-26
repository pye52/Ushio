package com.kanade.ushio.arch.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kanade.ushio.api.SubjectService
import com.kanade.ushio.api.UserCollectionService
import com.kanade.ushio.arch.repository.CalendarRepository
import com.kanade.ushio.arch.room.CalendarDao
import com.kanade.ushio.arch.room.CalendarSubjectDao
import com.kanade.ushio.arch.room.UserCollectionDao
import com.kanade.ushio.arch.viewmodel.CalendarViewModel

class CalendarViewModelFactory(private var service: SubjectService,
                               private var userCollectionDao: UserCollectionDao,
                               private var userCollectionService: UserCollectionService,
                               private var calendarDao: CalendarDao,
                               private var calendarSubjectDao: CalendarSubjectDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            val repository = CalendarRepository(calendarDao, calendarSubjectDao)
            return CalendarViewModel(service, userCollectionDao, userCollectionService, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}