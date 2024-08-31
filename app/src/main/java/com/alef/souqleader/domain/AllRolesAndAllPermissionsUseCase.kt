package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissions
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissionsResponse



import javax.inject.Inject

class AllRolesAndAllPermissionsUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getAllRoles(): Resource<AllRolesAndAllPermissionsResponse> {
        return repository.getAllRoles()
    }

    suspend fun getAllPermissions(): Resource<AllRolesAndAllPermissionsResponse> {
        return repository.getAllPermissions()
    }
}