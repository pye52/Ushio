package com.kanade.ushio.arch.viewmodel

import android.arch.lifecycle.ViewModel
import com.kanade.ushio.arch.repository.UserRepository
import com.kanade.ushio.entity.User
import com.kanade.ushio.entity.UserCollection
import com.kanade.ushio.utils.getUserId
import io.reactivex.Flowable

class UserViewModel(private var repository: UserRepository) : ViewModel() {
    fun queryUser(): Flowable<User> {
        val userId = getUserId()
        return repository.queryUser(userId.toString())
    }

    fun queryCollection(): Flowable<List<UserCollection>> {
        val userId = getUserId()
        return repository.queryCollection(userId.toString())
    }

    fun queryCollectionFromServer(): Flowable<List<UserCollection>> {
        val userId = getUserId()
        return repository.queryCollectionFromServer(userId.toString())
    }
}