package com.alef.souqleader.data.remote


import android.util.Log
import com.alef.souqleader.Resource
import com.alef.souqleader.data.remote.dto.AddLeadResponse
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.AddressFilterResponse
import com.alef.souqleader.data.remote.dto.AllJobResponse
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissionsResponse
import com.alef.souqleader.data.remote.dto.AllUserResponse
import com.alef.souqleader.data.remote.dto.BasicDataResponse
import com.alef.souqleader.data.remote.dto.CampaignResponse
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.CancelationReportResponse
import com.alef.souqleader.data.remote.dto.CategoryResponse
import com.alef.souqleader.data.remote.dto.ChangePasswordResponse
import com.alef.souqleader.data.remote.dto.ChannelReportResponse
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.CommentsResponse
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.DelayReportResponse
import com.alef.souqleader.data.remote.dto.FilterRequest
import com.alef.souqleader.data.remote.dto.ForgetPasswordResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.JobAppRequest
import com.alef.souqleader.data.remote.dto.JobAppsResponse
import com.alef.souqleader.data.remote.dto.LeadDetailsResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.LocationFilterDataResponse
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.MarketerResponse
import com.alef.souqleader.data.remote.dto.MeetingReportResponse
import com.alef.souqleader.data.remote.dto.PlanResponse
import com.alef.souqleader.data.remote.dto.PostResponse
import com.alef.souqleader.data.remote.dto.ProjectFilterDataResponse
import com.alef.souqleader.data.remote.dto.ProjectFilterRequest
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.ProjectsReportResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.data.remote.dto.RegionsResponse
import com.alef.souqleader.data.remote.dto.SalesProfileReportRequest
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.data.remote.dto.TimelinePostResponse
import com.alef.souqleader.data.remote.dto.UnlockResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.data.remote.dto.UserDateResponse
import com.alef.souqleader.data.remote.dto.UserDetailsResponse
import com.alef.souqleader.data.remote.dto.WhatMessageResponse
import com.alef.souqleader.domain.model.AddLead
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Query
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

    suspend fun leadDetails(
        id: String
    ): Resource<LeadDetailsResponse> {
        val response = APIs.leadDetails(id)
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


    suspend fun projects(page: Int): Resource<ProjectResponse> {
        val response = APIs.project(page)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun property(page: Int): Resource<PropertyResponse> {
        val response = APIs.getProperty(page)
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


    suspend fun getCompanyPost(page: Int): Resource<PostResponse> {
        val response = APIs.getCompanyPost(page)
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

    suspend fun quickCreate(
        name: String,
        phone: String?,
        duration: String?,
        note: String?,
        id:String?
    ): Resource<LeadsStatusResponse> {
        val response = APIs.quickCreate(name, phone, duration, note,id = id)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun userData(
        id: String, page: Int, activityPage: Int,
    ): Resource<UserDateResponse> {
        val response = APIs.userData(id, page.toString(), activityPage.toString())
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun cancelationReport(userId: String,page: Int): Resource<CancelationReportResponse> {
        val response = APIs.cancelationReport(userId,page)
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
    suspend fun logout(): Resource<StatusResponse> {
        val response = APIs.logout()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun getuserbytoken(): Resource<LoginResponse> {
        val response = APIs.getuserbytoken()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun userDetails(user: String): Resource<UserDetailsResponse> {
        val response = APIs.userDetails(user)
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

    suspend fun companyComment(comment: String, post_id: String): Resource<StatusResponse> {
        val response = APIs.companyComment(
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

    suspend fun companyLike(like: String, post_id: String): Resource<AddLikeResponse> {
        val response = APIs.companyLike(
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

    suspend fun deletePost(
        id: String
    ): Resource<StatusResponse> {
        val response = APIs.deletePost(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun deleteCompanyPost(
        id: String
    ): Resource<StatusResponse> {
        val response = APIs.deleteCompanyPost(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun timelinePost(
        id: String
    ): Resource<TimelinePostResponse> {
        val response = APIs.timelinePost(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun companyPost(
        id: String
    ): Resource<TimelinePostResponse> {
        val response = APIs.companyPost(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun deleteComment(
        id: String
    ): Resource<StatusResponse> {
        val response = APIs.deleteComment(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun deleteCompanyComment(
        id: String
    ): Resource<StatusResponse> {
        val response = APIs.deleteCompanyComment(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun getComments(
        id: String
    ): Resource<CommentsResponse> {
        val response = APIs.getComments(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun getCompanyComment(
        id: String
    ): Resource<CommentsResponse> {
        val response = APIs.getCompanyComment(
            id
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun addCompanyPost(
        post: RequestBody, images: ArrayList<MultipartBody.Part>?
    ): Resource<StatusResponse> {
        val response = APIs.addCompanyPost(
            post, images
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun getMeetingReport(page:String): Resource<MeetingReportResponse> {
        val response = APIs.getMeetingReport(page)
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

    suspend fun addressFilter(): Resource<AddressFilterResponse> {
        val response = APIs.addressFilter()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun basicData(): Resource<BasicDataResponse> {
        val response = APIs.basicData()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun allJob(pageNumber:Int): Resource<AllJobResponse> {
        val response = APIs.allJobs(page = pageNumber)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun jobapps(jobAppRequest: JobAppRequest): Resource<JobAppsResponse> {
        val response = APIs.jobapps(
            job_id = jobAppRequest.jobId,
            category_id = jobAppRequest.categoryId,
            type_id = jobAppRequest.typeId, career_level_id = jobAppRequest.careerLevelId,
            workplace_id = jobAppRequest.workplaceId, name = jobAppRequest.name,
            country_id = jobAppRequest.countryId,
            city_id = jobAppRequest.cityId, area_id = jobAppRequest.areaId
        )
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun unlock(id: String): Resource<UnlockResponse> {
        val response = APIs.unlock(
            id = id,
        )
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
    ): Resource<ForgetPasswordResponse> {
        val response = APIs.resetPassword(email, password, passwordConfirmation, code)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun contactus(
        name: String?,
        email: String?,
        phone: String?,
        organizationName: String?,
        message: String?
    ): Resource<ForgetPasswordResponse> {
        val response = APIs.contactus(name, email, phone, organizationName, message)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun checkCode(code: String): Resource<ForgetPasswordResponse> {
        val response = APIs.checkCode(code)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun forgetPassword(email: String): Resource<ForgetPasswordResponse> {
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
            filterRequest.budget_from,
            filterRequest.budget_to,
            filterRequest.marketer,
            filterRequest.communication_way,
            filterRequest.region,
            searchField = filterRequest.searchField,
            page = filterRequest.page
        )
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun regions(): Resource<RegionsResponse> {
        val response = APIs.regions()
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun projectFilterData(): Resource<ProjectFilterDataResponse> {
        val response = APIs.projectFilterData()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun propertyFilterData(): Resource<ProjectFilterDataResponse> {
        val response = APIs.propertyFilterData()
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun locationFilterData(countryId:String?,cityId:String?): Resource<LocationFilterDataResponse> {
        val response = APIs.locationFilterData(countryId,cityId)
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun propertyLocationFilterData(countryId:String?,cityId:String?): Resource<LocationFilterDataResponse> {
        val response = APIs.propertyLocationFilterData(countryId, cityId)
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }



    suspend fun propertyView(): Resource<RegionsResponse> {
        val response = APIs.propertyView()
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun propertyCategory(): Resource<CategoryResponse> {
        val response = APIs.propertyCategory()
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun propertyFinishing(): Resource<RegionsResponse> {
        val response = APIs.propertyFinishing()
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun projectFilter(page: Int,filterRequest: ProjectFilterRequest): Resource<ProjectResponse> {
        val response = APIs.projectFilter(
            page=page,
            filterRequest.title,
            filterRequest.category,
            filterRequest.countryId,
            filterRequest.cityId,
            filterRequest.areaId,
            filterRequest.budget_from,
            filterRequest.budget_to

        )
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


    suspend fun projectSort(page: Int): Resource<ProjectResponse> {
        val response = APIs.projectSort(page)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun propertySort(page: Int): Resource<PropertyResponse> {
        val response = APIs.propertySort(page)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun propertyFilter(page: Int,filterRequest: ProjectFilterRequest): Resource<PropertyResponse> {
        val response = APIs.propertyFilter(
            page = page,
            filterRequest.finishing,
            filterRequest.region,
            filterRequest.name,
            filterRequest.view,
            filterRequest.category,
            filterRequest.typeInventory,
            filterRequest.department,
            filterRequest.countryId,
            filterRequest.cityId,
            filterRequest.areaId,
            filterRequest.budget_from,
            filterRequest.budget_to
        )
        return if (response.isSuccessful) {

            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }

    suspend fun sendWhatsappMessage(message: String,isSaved:Boolean,checkLeads:List<String>): Resource<ForgetPasswordResponse> {
        val checkLeadsMap = mutableMapOf<String, String>().apply {
            checkLeads.forEachIndexed { index, value ->
                put("checkLeads[$index]", value)
            }
        }
        val response = APIs.sendWhatsappMessage(message,isSaved,checkLeadsMap)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun sendMail(subject: String,body:String
                         ,fromEmail:String,isSaved:Boolean,
                         isHtml:Boolean, ids:List<Int>): Resource<ForgetPasswordResponse> {
        val idss = mutableMapOf<String, Int>().apply {
            ids.forEachIndexed { index, value ->
                put("ids[$index]", value)
            }
        }
        val response = APIs.sendMail(subject,body,fromEmail,isSaved,isHtml,idss)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun sendSms(to: String,message:String
                         ,from:String): Resource<ForgetPasswordResponse> {
        val response = APIs.sendSms(to,message,from)
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun prevMessages(): Resource<WhatMessageResponse> {
        val response = APIs.prevMessages()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }
    suspend fun prevMails(): Resource<WhatMessageResponse> {
        val response = APIs.prevMails()
        return if (response.isSuccessful) {
            Resource.Success(response.body()!!, response.errorBody())
        } else {
            Resource.DataError(null, response.code(), response.errorBody())
        }
    }


}
