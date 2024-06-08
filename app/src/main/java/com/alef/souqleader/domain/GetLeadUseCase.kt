package com.alef.souqleader.domain

import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.ApiRepoImpl
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


import javax.inject.Inject

class GetLeadUseCase @Inject constructor(private val repository: ApiRepoImpl) {
    suspend fun getLeadStatus(): Resource<LeadsStatusResponse> {
        return  repository.leadsStatus()
    }
    suspend fun getLeadByStatus(id:String): Resource<LeadsByStatusResponse> {
        return  repository.leadsByStatus(id)
    }
     suspend fun result(
        res: (Resource<LeadsStatusResponse>) -> Unit
    ) {
      //  repository.symbols().symbols
//        flow {
//            emit(repository.symbols())
//        }.flowOn(Dispatchers.IO)
//            .catch {
//                it.message?.let {
//                    Resource.DataError(
//                        null,
//                        0, null
//                    )
//                }
//            }
//            .buffer().collect {
//                res(it)
//            }
    }
}