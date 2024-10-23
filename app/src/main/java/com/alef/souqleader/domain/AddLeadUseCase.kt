package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AddLeadResponse
import com.alef.souqleader.data.remote.dto.CampaignResponse
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.MarketerResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.domain.model.AddLead
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody


import javax.inject.Inject

class AddLeadUseCase @Inject constructor(private val repository: ApiRepoImpl) {


    suspend fun communicationWay(): Resource<CommunicationWayResponse> {
        return repository.communicationWay()
    }

    suspend fun allMarketer(): Resource<MarketerResponse> {
        return repository.allMarketer()
    }

    suspend fun allSales(): Resource<SalesResponse> {
        return repository.allSales()
    }

    suspend fun campaign(): Resource<CampaignResponse> {
        return repository.campaign()
    }

    suspend fun channel(): Resource<ChannelResponse> {
        return repository.channel()
    }

    suspend fun lead(addLead: AddLead): Flow<Resource<AddLeadResponse>> {
        return flow {
            emit(repository.lead(addLead))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun project(page:Int): Resource<ProjectResponse> {
        return repository.projects(page = page)
    }

}