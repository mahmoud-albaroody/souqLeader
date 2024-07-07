package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.GetClientResponse


import javax.inject.Inject

class SimplifyUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun getClient(username: String): Resource<GetClientResponse> {
        return repository.getClient(username)
    }
}