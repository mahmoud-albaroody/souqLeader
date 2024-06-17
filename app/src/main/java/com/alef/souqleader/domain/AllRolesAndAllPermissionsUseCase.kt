package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissions
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissionsResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.MultiResponse
import com.alef.souqleader.data.remote.dto.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class AllRolesAndAllPermissionsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getAllRoles(): Resource<AllRolesAndAllPermissionsResponse> {
        return repository.getAllRoles()
    }

    suspend fun getAllPermissions(): Resource<AllRolesAndAllPermissionsResponse> {
        return repository.getAllPermissions()
    }
}