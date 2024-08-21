package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.data.remote.dto.UserDateResponse


import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun userDate(id: String): Resource<UserDateResponse> {
        return repository.userData(id)
    }


}