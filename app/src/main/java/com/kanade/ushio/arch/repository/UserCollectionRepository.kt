package com.kanade.ushio.arch.repository

import com.kanade.ushio.api.UserCollectionService
import com.kanade.ushio.arch.room.UserCollectionDao
import com.kanade.ushio.arch.room.SubjectDao
import com.kanade.ushio.entity.UserCollection
import com.kanade.ushio.entity.Subject
import io.reactivex.Flowable

class UserCollectionRepository(private var service: UserCollectionService,
                               private var userCollectionDao: UserCollectionDao,
                               private var subjectDao: SubjectDao) {
    fun getCollection(username: String): Flowable<List<UserCollection>> {
        return service.getUserCollection(username)
    }

    fun queryAllUserCollection(): Flowable<List<UserCollection>> {
        return userCollectionDao.queryUserCollection()
    }

    fun insertSubject(subject: Subject) :Long{
        return subjectDao.insertSubject(subject)
    }

    fun insertUserCollection(collection: UserCollection): Long {
        return userCollectionDao.insertUserCollection(collection)
    }
}