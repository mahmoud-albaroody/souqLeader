package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun addPost(post: RequestBody,
                        images: ArrayList<MultipartBody.Part>?): Resource<StatusResponse> {
        return repository.addPost(post,images)
    }
}