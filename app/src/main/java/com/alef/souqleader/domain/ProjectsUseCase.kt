package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class ProjectsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun project(page:Int): Resource<ProjectResponse> {
        return repository.projects(page)
    }

    suspend fun property(page:Int): Resource<PropertyResponse> {
        return repository.property(page)
    }

    suspend fun projectSort(page:Int): Flow<Resource<ProjectResponse>> {
        return flow {
            emit(repository.projectSort(page))
        }.flowOn(Dispatchers.IO)
    }
    suspend fun propertySort(page:Int): Flow<Resource<PropertyResponse>> {
        return flow {
            emit(repository.propertySort(page))
        }.flowOn(Dispatchers.IO)
    }

}