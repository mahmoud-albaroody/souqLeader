package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse



import javax.inject.Inject

class ProjectsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun project(page:Int): Resource<ProjectResponse> {
        return repository.projects(page)
    }

    suspend fun property(page:Int): Resource<PropertyResponse> {
        return repository.property(page)
    }
}