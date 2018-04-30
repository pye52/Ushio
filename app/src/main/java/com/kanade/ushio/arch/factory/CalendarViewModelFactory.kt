package com.kanade.ushio.arch.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kanade.ushio.arch.repository.CalendarRepository
import com.kanade.ushio.arch.repository.SubjectRepository
import com.kanade.ushio.arch.repository.UserRepository
import com.kanade.ushio.arch.viewmodel.CalendarViewModel

class CalendarViewModelFactory(private var calendarRepository: CalendarRepository,
                               private var subjectRepository: SubjectRepository,
                               private var userCollectionRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            return CalendarViewModel(subjectRepository, userCollectionRepository, calendarRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}