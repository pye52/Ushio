package com.kanade.ushio.arch.repository

import com.kanade.ushio.api.UserService
import com.kanade.ushio.arch.room.UserCollectionDao
import com.kanade.ushio.entity.User
import com.kanade.ushio.entity.UserCollection
import io.reactivex.Flowable

class UserRepository(private var service: UserService,
                     private var userCollectionDao: UserCollectionDao,
                     private var subjectRepo: SubjectRepository) {
    fun queryUser(username: String): Flowable<User> {
        return service.queryUser(username)
    }

    fun queryCollection(username: String): Flowable<List<UserCollection>> {
        return userCollectionDao.queryUserCollection()
                // 当数据库没有数据，则向服务器请求
                // 但此时不需要往下处理，因为arch架构会在数据库更新时发出响应
                .doOnNext { list ->
                    if (list.isEmpty()) {
                        val call = service.queryUserCollectionSync(username)
                        val subjects = call.execute()
                                .body()
                        subjects?.let {
                            saveSubjects(it)
                        }
                    }
                }
    }

    fun queryCollectionFromServer(username: String): Flowable<List<UserCollection>> {
        return service.queryUserCollection(username)
                .doOnNext { list ->
                    saveSubjects(list)
                }
    }

    fun deleteCollectionById(id: Long) {
        userCollectionDao.deleteUserCollectionById(id)
    }

    // 注意不要覆盖其onAir值
    private fun saveSubjects(list: List<UserCollection>) {
        list.forEach { collections ->
            collections.subject?.let { subject ->
                subject.holdSubject()
                subjectRepo.insertSubject(subject)
            }
        }
        userCollectionDao.insertUserCollection(list)
    }
}