package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.StatusResponse


import javax.inject.Inject

class AddLikeUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun addLike(like: String, postId: String): Resource<AddLikeResponse> {
        return repository.like(like,postId)
    }


}