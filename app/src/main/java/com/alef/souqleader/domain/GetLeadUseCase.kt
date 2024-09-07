package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class GetLeadUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getLeadStatus(): Flow<Resource<LeadsStatusResponse>> {
        return flow {
            emit(repository.leadsStatus())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun coldLeadStatus(): Resource<LeadsByStatusResponse> {
        return repository.coldLeadStatus()
    }

    suspend fun delayLeads(page: Int): Flow<Resource<LeadsByStatusResponse>> {
        return flow {
            emit(repository.delayLeads(page))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun freshLeadStatus(): Resource<LeadsByStatusResponse> {
        return repository.freshLeadStatus()
    }

    suspend fun duplicated(page: Int): Flow<Resource<LeadsByStatusResponse>> {
        return flow {
            emit(repository.duplicated(page))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getLeadByStatus(id: String, page: Int): Flow<Resource<LeadsByStatusResponse>> {
        return flow {
            emit(repository.leadsByStatus(id, page))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateLead(
        id: String,
        status: String?,
        note: String?,
        reminderTime: String?,
        cancelReason: String?
    ): Resource<UpdateLeadResponse> {
        return repository.updateLead(id, status, note, reminderTime, cancelReason)
    }

    suspend fun cancelationReason(): Resource<CancelationReasonResponse> {
        return repository.cancelationReason()
    }


}