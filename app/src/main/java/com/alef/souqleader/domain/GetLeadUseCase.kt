package com.alef.souqleader.domain

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.BasicDataResponse
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.ForgetPasswordResponse
import com.alef.souqleader.data.remote.dto.LeadDetailsResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.data.remote.dto.WhatMessageResponse
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
    suspend fun leadDetails(id: String): Flow<Resource<LeadDetailsResponse>> {
        return flow {
            emit(repository.leadDetails(id))
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

    suspend fun quickCreate(
        name: String,
        phone: String?,
        duration: String?,
        note: String?,
        id:String?
    ): Resource<LeadsStatusResponse> {
        return repository.quickCreate(name, phone, duration, note,id)
    }
    suspend fun sendWhatsappMessage(
        message: String,isSaved:Boolean,checkLeads: List<String>
    ): Resource<ForgetPasswordResponse> {
        return repository.sendWhatsappMessage(message, isSaved, checkLeads)
    }
    suspend fun sendMail(
        subject: String,body:String
        ,fromEmail:String,isSaved:Boolean,
        isHtml:Boolean, ids:List<Int>
    ): Resource<ForgetPasswordResponse> {
        return repository.sendMail(subject,body,fromEmail,isSaved,isHtml,ids)
    }
    suspend fun sendSms(
        to: String,message:String
        ,from:String
    ): Resource<ForgetPasswordResponse> {
        return repository.sendSms(to,message,from)
    }

    suspend fun prevMessages(): Resource<WhatMessageResponse> {
        return repository.prevMessages()
    }
    suspend fun prevMails(): Resource<WhatMessageResponse> {
        return repository.prevMails()
    }


    suspend fun cancelationReason(): Resource<CancelationReasonResponse> {
        return repository.cancelationReason()
    }


}