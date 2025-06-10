package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.ChannelReportResponse
import com.alef.souqleader.data.remote.dto.DelayReportResponse
import com.alef.souqleader.data.remote.dto.MeetingReportResponse
import com.alef.souqleader.data.remote.dto.ProjectsReportResponse


import javax.inject.Inject

class MeetingReportsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getMeetingReport(page:String): Resource<MeetingReportResponse> {
        return repository.getMeetingReport(page)
    }

    suspend fun channelReport(): Resource<ChannelReportResponse> {
        return repository.channelReport()
    }

    suspend fun projectsReport(): Resource<ProjectsReportResponse> {
        return repository.projectsReport()
    }

    suspend fun delayReport(): Resource<DelayReportResponse> {
        return repository.delayReport()
    }
}