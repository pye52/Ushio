package com.kanade.ushio.arch.viewmodel

import android.arch.lifecycle.ViewModel
import com.kanade.ushio.arch.repository.UserCollectionRepository
import com.kanade.ushio.entity.UserCollection
import com.kanade.ushio.utils.getUserId
import io.reactivex.Flowable

class UserCollectionViewModel(private var repository: UserCollectionRepository) : ViewModel() {

    fun queryCollection(): Flowable<List<UserCollection>> {
        return Flowable.concat(
                queryCollectionFromServer(),
                queryCollectionFromLocal())
                .filter { it.isNotEmpty() }
                .take(1)
    }

    private fun queryCollectionFromLocal(): Flowable<List<UserCollection>> {
        return repository.queryAllSmallSubject()
    }

    private fun queryCollectionFromServer(): Flowable<List<UserCollection>> {
        val userId = getUserId()
        return repository.getCollection(userId.toString())
                .doOnNext { list ->
                    list.forEach { ss ->
                        ss.subject?.let {
                            repository.insertSubject(it)
                        }
                        repository.insertSmallSubject(ss)
                    }
                }
    }
}