package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilterUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun leadsFilter(filterRequest: FilterRequest): Flow<Resource<LeadsByStatusResponse>> {
        return flow {
            emit(repository.leadsFilter(filterRequest))
        }.flowOn(Dispatchers.IO)
    }
}