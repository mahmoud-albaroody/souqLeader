package com.alef.souqleader.data.remote


import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import javax.inject.Inject


class ApiRepoImpl @Inject constructor(private val APIs: APIs) {
    suspend fun leadsStatus(): Resource<LeadsStatusResponse> {
        val response = APIs.leadStatus()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

        suspend fun leadsByStatus(
        id: String
    ): Resource<LeadsByStatusResponse> {
        val response = APIs.leadsByStatus(id)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

//    suspend fun symbols(): LeadsByStatusResponse {
//        val response = APIs.symbols()
//        if (response.isSuccessful) {
//            return response.body()!!
//        } else {
//            return response.body()!!
//        }
//    }
//
//     suspend fun getCurrencyData(date: String, base: String): Resource<CurrencyResponse> {
//        val response = APIs.getCurrencyData(date = date, base = base)
//        return if (response.isSuccessful) {
//            Resource.Success(response.body()!!, response.errorBody())
//        } else {
//            Resource.DataError(null, response.code(), response.errorBody())
//        }
//    }


}
