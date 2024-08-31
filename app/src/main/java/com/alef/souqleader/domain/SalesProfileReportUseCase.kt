package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse


import javax.inject.Inject

class SalesProfileReportUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getSalesProfileReport(): Resource<SalesProfileReportResponse> {
        return repository.getSalesProfileReport()
    }
}