package com.kanade.ushio.arch.viewmodel

import android.arch.lifecycle.ViewModel
import com.kanade.ushio.arch.repository.UserCollectionRepository
import com.kanade.ushio.entity.UserCollection
import com.kanade.ushio.utils.getUserId
import io.reactivex.Flowable

class UserCollectionViewModel(private var repository: UserCollectionRepository) : ViewModel() {

    fun queryCollection(): Flowable<List<UserCollection>> {
        return repository.queryAllUserCollection()
                .flatMap { list ->
                    if (list.isEmpty()) {
                        return@flatMap queryCollectionFromServer()
                    } else {
                        Flowable.just(list)
                    }
                }
    }

    fun queryCollectionFromLocal(): Flowable<List<UserCollection>> {
        return repository.queryAllUserCollection()
    }

    private fun queryCollectionFromServer(): Flowable<List<UserCollection>> {
        val userId = getUserId()
        return repository.getCollection(userId.toString())
                .doOnNext { list ->
                    list.forEach { ss ->
                        ss.subject?.let {
                            repository.insertSubject(it)
                        }
                        repository.insertUserCollection(ss)
                    }
                }
    }
}