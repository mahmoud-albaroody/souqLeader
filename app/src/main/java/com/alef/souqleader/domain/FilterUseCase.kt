package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import javax.inject.Inject

class FilterUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun leadsFilter(filterRequest: FilterRequest): Resource<LeadsByStatusResponse> {
        return repository.leadsFilter(filterRequest)
    }

}