package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.CancelationReportResponse


import javax.inject.Inject

class CancelationUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getCancelationReport(userId:String): Resource<CancelationReportResponse> {
        return  repository.cancelationReport(userId)
    }
}