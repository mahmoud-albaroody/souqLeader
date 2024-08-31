package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl

import com.alef.souqleader.data.remote.dto.UpdateLeadResponse



import javax.inject.Inject

class SaveMultiUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun updateMulti(
        ids: Array<String>, status: String?,
        note: String?,
        reminderTime: String?,
        cancelReason: String?
    ): Resource<UpdateLeadResponse> {
        return repository.updateMulti(
            ids, status,
            note,
            reminderTime,
            cancelReason
        )
    }
}