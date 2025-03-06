package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AddLeadResponse
import com.alef.souqleader.data.remote.dto.AllJobResponse
import com.alef.souqleader.data.remote.dto.AllUserResponse
import com.alef.souqleader.data.remote.dto.CampaignResponse
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.JobAppsResponse
import com.alef.souqleader.data.remote.dto.MarketerResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.domain.model.AddLead
import okhttp3.MultipartBody
import okhttp3.RequestBody


import javax.inject.Inject

class AllJobUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun allJob(): Resource<AllJobResponse> {
        return repository.allJob()
    }

    suspend fun JobApp(jobAppRequest:JobAppRequest): Resource<JobAppsResponse> {
        return repository.jobapps(jobAppRequest)
    }
}