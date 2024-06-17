package com.alef.souqleader.data.remote


import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissionsResponse
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.CancelationReportResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.MeetingReportResponse
import com.alef.souqleader.data.remote.dto.MultiResponse
import com.alef.souqleader.data.remote.dto.PlanResponse
import com.alef.souqleader.data.remote.dto.PostResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.data.remote.dto.SalesProfileReportRequest
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
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

    suspend fun updateMulti(
        ids: ArrayList<String>
    ): Resource<MultiResponse> {
        val response = APIs.updateMulti(ids)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun projects(): Resource<ProjectResponse> {
        val response = APIs.project()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun property(): Resource<PropertyResponse> {
        val response = APIs.getProperty()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun paymentPlan(): Resource<PlanResponse> {
        val response = APIs.plans()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun getPost(): Resource<PostResponse> {
        val response = APIs.getPost()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun getAllRoles(): Resource<AllRolesAndAllPermissionsResponse> {
        val response = APIs.getAllRoles()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun getAllPermissions(): Resource<AllRolesAndAllPermissionsResponse> {
        val response = APIs.getAllPermissions()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun getSalesProfileReport(): Resource<SalesProfileReportResponse> {
        val response = APIs.getSalesProfileReport("5")
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun cancelationReason(): Resource<CancelationReasonResponse> {
        val response = APIs.cancelationReason()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun cancelationReport(userId:String): Resource<CancelationReportResponse> {
        val response = APIs.cancelationReport(userId)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun getMeetingReport(): Resource<MeetingReportResponse> {
        val response = APIs.getMeetingReport()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
}
