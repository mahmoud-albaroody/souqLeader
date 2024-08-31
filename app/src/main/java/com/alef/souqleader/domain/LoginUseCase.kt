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

    suspend fun changePassword(
        password: String,
        newPassword: String,
        confirmPassword: String
    ): Resource<StatusResponse> {
        return repository.changePassword(password, newPassword, confirmPassword)
    }

    suspend fun resetPassword(
        email: String,
        password: String,
        passwordConfirmation: String,
        code: String
    ): Resource<StatusResponse> {
        return repository.resetPassword(email, password, passwordConfirmation,code)
    }

    suspend fun checkCode(
        code: String
    ): Resource<StatusResponse> {
        return repository.checkCode(code)
    }

    suspend fun forgetPassword(
        email:String
    ): Resource<StatusResponse> {
        return repository.forgetPassword(email)
    }


}