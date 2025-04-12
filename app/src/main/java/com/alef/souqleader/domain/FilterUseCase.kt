package com.alef.souqleader.domain

import android.util.Log
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.AddressFilterResponse
import com.alef.souqleader.data.remote.dto.BasicDataResponse
import com.alef.souqleader.data.remote.dto.CategoryResponse
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LocationFilterDataResponse
import com.alef.souqleader.data.remote.dto.ProjectFilterDataResponse
import com.alef.souqleader.data.remote.dto.ProjectFilterRequest
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.data.remote.dto.RegionsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FilterUseCase @Inject constructor(private val repository: ApiRepoImpl) {

    suspend fun leadsFilter(filterRequest: FilterRequest): Flow<Resource<LeadsByStatusResponse>> {
        return flow {
            emit(repository.leadsFilter(filterRequest))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun regions(): Flow<Resource<RegionsResponse>> {
        return flow {
            emit(repository.regions())
        }.flowOn(Dispatchers.IO)
    }
    suspend fun projectFilterData(): Flow<Resource<ProjectFilterDataResponse>> {
        return flow {
            emit(repository.projectFilterData())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun propertyFilterData(): Flow<Resource<ProjectFilterDataResponse>> {
        return flow {
            emit(repository.propertyFilterData())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun locationFilterData(countryId:String?,cityId:String?): Flow<Resource<LocationFilterDataResponse>> {
        return flow {
            emit(repository.locationFilterData(countryId,cityId))
        }.flowOn(Dispatchers.IO)
    }
    suspend fun propertyLocationFilterData(countryId:String?,cityId:String?): Flow<Resource<LocationFilterDataResponse>> {
        return flow {
            emit(repository.propertyLocationFilterData(countryId, cityId))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun propertyView(): Flow<Resource<RegionsResponse>> {
        return flow {
            emit(repository.propertyView())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun propertyCategory(): Flow<Resource<CategoryResponse>> {
        return flow {
            emit(repository.propertyCategory())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun propertyFinishing(): Flow<Resource<RegionsResponse>> {
        return flow {
            emit(repository.propertyFinishing())
        }.flowOn(Dispatchers.IO)
    }


    suspend fun projectFilter(filterRequest: ProjectFilterRequest): Flow<Resource<ProjectResponse>> {
        return flow {
            emit(repository.projectFilter(filterRequest))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun propertyFilter(filterRequest: ProjectFilterRequest): Flow<Resource<PropertyResponse>> {
        return flow {
            emit(repository.propertyFilter(filterRequest))
        }.flowOn(Dispatchers.IO)
    }


    suspend fun addressFilter(): Resource<AddressFilterResponse>{
        return repository.addressFilter()
    }
    suspend fun basicData(): Resource<BasicDataResponse>{
        return repository.basicData()
    }
}