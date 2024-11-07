package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.StatusResponse


import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun addComment(comment: String, post_id: String): Resource<StatusResponse> {
        return repository.comment(comment,post_id)
    }

    suspend fun addCompanyComment(comment: String, post_id: String): Resource<StatusResponse> {
        return repository.companyComment(comment,post_id)
    }


}