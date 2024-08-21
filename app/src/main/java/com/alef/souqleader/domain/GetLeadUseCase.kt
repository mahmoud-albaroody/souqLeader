package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse


import javax.inject.Inject

class GetLeadUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getLeadStatus(): Resource<LeadsStatusResponse> {
        return repository.leadsStatus()
    }

    suspend fun coldLeadStatus(): Resource<LeadsByStatusResponse> {
        return repository.coldLeadStatus()
    }

    suspend fun delayLeads(): Resource<LeadsByStatusResponse> {
        return repository.delayLeads()
    }

    suspend fun freshLeadStatus(): Resource<LeadsByStatusResponse> {
        return repository.freshLeadStatus()
    }

    suspend fun duplicated(): Resource<LeadsByStatusResponse> {
        return repository.duplicated()
    }

    suspend fun getLeadByStatus(id: String): Resource<LeadsByStatusResponse> {
        return repository.leadsByStatus(id)
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