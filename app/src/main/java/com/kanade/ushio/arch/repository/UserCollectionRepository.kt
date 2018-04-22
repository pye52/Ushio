package com.kanade.ushio.arch.repository

import com.kanade.ushio.api.UserCollectionService
import com.kanade.ushio.arch.room.SmallSubjectDao
import com.kanade.ushio.arch.room.SubjectDao
import com.kanade.ushio.entity.UserCollection
import com.kanade.ushio.entity.Subject
import io.reactivex.Flowable

class UserCollectionRepository(private var service: UserCollectionService,
                               private var smallSubjectDao: SmallSubjectDao,
                               private var subjectDao: SubjectDao) {
    fun getCollection(username: String): Flowable<List<UserCollection>> {
        return service.getUserCollection(username)
    }

    fun queryAllSmallSubject(): Flowable<List<UserCollection>> {
        return smallSubjectDao.querySmallSubject()
    }

    fun insertSubject(subject: Subject) :Long{
        return subjectDao.insertSubject(subject)
    }

    fun insertSmallSubject(smallSubject: UserCollection): Long {
        return smallSubjectDao.insertSmallSubject(smallSubject)
    }
}