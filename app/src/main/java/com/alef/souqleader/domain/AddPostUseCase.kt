package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.CommentsResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.data.remote.dto.TimelinePostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody


import javax.inject.Inject

class AddPostUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun addPost(post: RequestBody,
                        images: ArrayList<MultipartBody.Part>?): Resource<StatusResponse> {
        return repository.addPost(post,images)
    }
    suspend fun deletePost(id:String): Resource<StatusResponse> {
        return repository.deletePost(id)
    }

    suspend fun companyPost(id:String): Resource<TimelinePostResponse> {
        return repository.companyPost(id)
    }

    suspend fun timelinePost(id:String): Resource<TimelinePostResponse> {
        return repository.timelinePost(id)
    }
    suspend fun deleteComment(id:String): Resource<StatusResponse> {
        return repository.deleteComment(id)
    }
    suspend fun deleteCompanyComment(id:String): Resource<StatusResponse> {
        return repository.deleteCompanyComment(id)
    }

    suspend fun getComments(id:String): Resource<CommentsResponse> {
        return repository.getComments(id)
    }
    suspend fun getCompanyComment(id:String): Resource<CommentsResponse> {
        return repository.getCompanyComment(id)
    }

    suspend fun addCompanyPost(post: RequestBody,
                        images: ArrayList<MultipartBody.Part>?): Resource<StatusResponse> {
        return repository.addCompanyPost(post,images)
    }

}