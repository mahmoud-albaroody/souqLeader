package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse



import javax.inject.Inject

class ProjectsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun project(): Resource<ProjectResponse> {
        return repository.projects()
    }

    suspend fun property(): Resource<PropertyResponse> {
        return repository.property()
    }
}