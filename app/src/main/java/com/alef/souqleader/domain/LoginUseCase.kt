package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.StatusResponse


import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: ApiRepoImpl
) {
    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        return repository.login(username, password)
    }

    suspend fun updateFcmToken(token: String): Resource<StatusResponse> {
        return repository.updateFcmToken(token)
    }



}