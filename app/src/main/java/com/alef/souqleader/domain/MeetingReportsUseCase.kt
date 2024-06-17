package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.MeetingReportResponse


import javax.inject.Inject

class MeetingReportsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getMeetingReport(): Resource<MeetingReportResponse> {
        return repository.getMeetingReport()
    }
}