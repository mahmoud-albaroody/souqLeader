package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.PostResponse



import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getPosts(): Resource<PostResponse> {
        return  repository.getPost()
    }
}