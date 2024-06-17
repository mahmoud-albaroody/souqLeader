package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.MultiResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class ProjectsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun project(): Resource<ProjectResponse> {
        return repository.projects()
    }

    suspend fun property(): Resource<PropertyResponse> {
        return repository.property()
    }
}