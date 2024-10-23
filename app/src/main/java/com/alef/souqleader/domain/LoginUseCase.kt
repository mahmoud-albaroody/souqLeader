package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.ChangePasswordResponse
import com.alef.souqleader.data.remote.dto.ForgetPasswordResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: ApiRepoImpl
) {
    suspend fun login(username: String, password: String): Flow<Resource<LoginResponse>> {
        return flow {
            emit(repository.login(username, password))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateFcmToken(token: String): Resource<StatusResponse> {
        return repository.updateFcmToken(token)
    }

    suspend fun changePassword(
        password: String,
        newPassword: String,
        confirmPassword: String
    ): Resource<ChangePasswordResponse> {
        return repository.changePassword(password, newPassword, confirmPassword)
    }

    suspend fun resetPassword(
        email: String,
        password: String,
        passwordConfirmation: String,
        code: String
    ): Resource<ForgetPasswordResponse> {
        return repository.resetPassword(email, password, passwordConfirmation, code)
    }

    suspend fun contactus(
        name: String?,
        email: String?,
        phone: String?,
        organizationName: String?,
        message: String?
    ): Resource<ForgetPasswordResponse> {
        return repository.contactus(name, email, phone, organizationName, message)
    }

    suspend fun checkCode(
        code: String
    ): Resource<ForgetPasswordResponse> {
        return repository.checkCode(code)
    }

    suspend fun forgetPassword(
        email: String
    ): Resource<ForgetPasswordResponse> {
        return repository.forgetPassword(email)
    }


}