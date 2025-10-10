package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.DashboardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DashboardUseCase  @Inject constructor(
    private val repository: ApiRepoImpl
) {
    suspend fun dashboard(): Flow<Resource<DashboardResponse>> {
        return flow {
            emit(repository.dashboard())
        }.flowOn(Dispatchers.IO)
    }
}