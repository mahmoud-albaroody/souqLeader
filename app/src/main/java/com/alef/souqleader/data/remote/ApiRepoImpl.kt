package com.alef.souqleader.data.remote


import android.util.Log
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AddLeadResponse
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissionsResponse
import com.alef.souqleader.data.remote.dto.AllUserResponse
import com.alef.souqleader.data.remote.dto.CampaignResponse
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.CancelationReportResponse
import com.alef.souqleader.data.remote.dto.ChangePasswordResponse
import com.alef.souqleader.data.remote.dto.ChannelReportResponse
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.DelayReportResponse
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.MarketerResponse
import com.alef.souqleader.data.remote.dto.MeetingReportResponse
import com.alef.souqleader.data.remote.dto.PlanResponse
import com.alef.souqleader.data.remote.dto.PostResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.ProjectsReportResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.data.remote.dto.SalesProfileReportRequest
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.data.remote.dto.UserDateResponse
import com.alef.souqleader.domain.model.AddLead
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun duplicated(page: Int): Resource<LeadsByStatusResponse> {
        val response = APIs.duplicated(page)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun freshLeadStatus(): Resource<LeadsByStatusResponse> {
        val response = APIs.freshLeadStatus()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun delayLeads(page: Int): Resource<LeadsByStatusResponse> {
        val response = APIs.delayLeads(page)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun coldLeadStatus(): Resource<LeadsByStatusResponse> {
        val response = APIs.coldLeadStatus()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun leadsByStatus(
        id: String, page: Int
    ): Resource<LeadsByStatusResponse> {
        val response = APIs.leadsByStatus(id, page)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun updateMulti(
        ids: Array<String>, status: String?,
        note: String?,
        reminderTime: String?,
        cancelReason: String?
    ): Resource<UpdateLeadResponse> {
        val response = APIs.updateMulti(
            ids, status,
            note,
            reminderTime,
            cancelReason
        )
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

    suspend fun getPost(page: Int): Resource<PostResponse> {
        val response = APIs.getPost(page)
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

    suspend fun getSalesProfileReport(userId: String): Resource<SalesProfileReportResponse> {
        val response = APIs.getSalesProfileReport(user_id = userId)
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

    suspend fun updateLead(
        id: String,
        status: String?,
        note: String?,
        reminderTime: String?,
        cancelReason: String?
    ): Resource<UpdateLeadResponse> {
        val response = APIs.updateLead(id, status, note, reminderTime, cancelReason)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun userData(
        id: String
    ): Resource<UserDateResponse> {
        val response = APIs.userData(id)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun cancelationReport(userId: String): Resource<CancelationReportResponse> {
        val response = APIs.cancelationReport(userId)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun login(username: String, password: String): Resource<LoginResponse> {
        val response = APIs.login(username, password)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun getClient(username: String): Resource<GetClientResponse> {
        val response = APIs.getClient(username)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun comment(comment: String, post_id: String): Resource<StatusResponse> {
        val response = APIs.comment(
            comment, post_id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun like(like: String, post_id: String): Resource<AddLikeResponse> {
        val response = APIs.like(
            like, post_id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun updateFcmToken(token: String): Resource<StatusResponse> {
        val response = APIs.updateFcmToken(
            token
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun communicationWay(): Resource<CommunicationWayResponse> {
        val response = APIs.communicationWay()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun allMarketer(): Resource<MarketerResponse> {
        val response = APIs.allMarketer()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun allSales(): Resource<SalesResponse> {
        val response = APIs.allSales()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun campaign(): Resource<CampaignResponse> {
        val response = APIs.campaign()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun channel(): Resource<ChannelResponse> {
        val response = APIs.channel()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun lead(addLead: AddLead): Resource<AddLeadResponse> {
        val response = APIs.lead(
            name = addLead.name,
            email = addLead.email,
            phone = addLead.phone,
            note = addLead.note,
            marketer_id = addLead.marketer_id,
            sales_id = addLead.sales_id,
            project_id = addLead.project_id,
            channel = addLead.channel,
            communication_way = addLead.communication_way,
            cancel_reason = addLead.cancel_reason,
            action_date = addLead.action_date,
            budget = addLead.budget,
            is_fresh = addLead.is_fresh
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun addPost(
        post: RequestBody, images: ArrayList<MultipartBody.Part>?
    ): Resource<StatusResponse> {
        val response = APIs.addPost(
            post, images
        )
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

    suspend fun channelReport(): Resource<ChannelReportResponse> {
        val response = APIs.channelStatistics()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun projectsReport(): Resource<ProjectsReportResponse> {
        val response = APIs.projectsReport()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun delayReport(): Resource<DelayReportResponse> {
        val response = APIs.delayReport()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun allUsers(): Resource<AllUserResponse> {
        val response = APIs.allUsers()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun changePassword(
        password: String,
        newPassword: String,
        confirmPassword: String
    ): Resource<ChangePasswordResponse> {
        val response = APIs.changePassword(password, newPassword, confirmPassword)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun resetPassword(
        email: String,
        password: String,
        passwordConfirmation: String,
        code: String
    ): Resource<StatusResponse> {
        val response = APIs.resetPassword(email, password, passwordConfirmation, code)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun checkCode(code: String): Resource<StatusResponse> {
        val response = APIs.checkCode(code)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun forgetPassword(email: String): Resource<StatusResponse> {
        val response = APIs.forgetPassword(email)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun leadsFilter(filterRequest: FilterRequest): Resource<LeadsByStatusResponse> {
        val response = APIs.leadsFilter(
            filterRequest.phone,
            filterRequest.status,
            filterRequest.name,
            filterRequest.note,
            filterRequest.channel,
            filterRequest.sales,
            filterRequest.project,
            filterRequest.budget,
            filterRequest.marketer,
            filterRequest.communication_way,
            filterRequest.region,
            page = filterRequest.page
        )
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


}
