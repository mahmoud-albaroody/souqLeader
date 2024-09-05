package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class SimplifyUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun getClient(username: String): Flow<Resource<GetClientResponse>> {
        return flow {
            emit(repository.getClient(username))
        }.flowOn(Dispatchers.IO)
    }
}