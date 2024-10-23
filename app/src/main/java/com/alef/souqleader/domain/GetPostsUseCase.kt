package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getPosts(page: Int)
            : Flow<Resource<PostResponse>> {
        return flow {
            emit(repository.getPost(page))
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getCompanyPost(page: Int)
            : Flow<Resource<PostResponse>> {
        return flow {
            emit(repository.getCompanyPost(page))
        }.flowOn(Dispatchers.IO)
    }


}