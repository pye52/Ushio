/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kanade.ushio.arch.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.kanade.ushio.api.AuthService
import com.kanade.ushio.arch.repository.UserTokenRepository
import com.kanade.ushio.arch.room.UserTokenDao
import com.kanade.ushio.arch.viewmodel.LoginViewModel

/**
 * Factory for ViewModels
 */
class UserTokenDaoViewModelFactory(private val service: AuthService, private val dataSource: UserTokenDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val repository = UserTokenRepository(dataSource)
            return LoginViewModel(service, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
