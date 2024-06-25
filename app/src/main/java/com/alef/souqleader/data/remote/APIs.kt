package com.alef.souqleader.data.remote

import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissionsResponse
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.CancelationReportResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.MeetingReportResponse
import com.alef.souqleader.data.remote.dto.MultiResponse
import com.alef.souqleader.data.remote.dto.PlanResponse
import com.alef.souqleader.data.remote.dto.PostResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.data.remote.dto.SalesProfileReportRequest
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import retrofit2.Response
import retrofit2.http.*


interface APIs {


    @GET("api/leadStatus")
    suspend fun leadStatus(): Response<LeadsStatusResponse>

    @GET("api/leadsByStatus")
    suspend fun leadsByStatus(@Query("id") id: String): Response<LeadsByStatusResponse>

    @FormUrlEncoded
    @POST("api/updateMulti")
    suspend fun updateMulti(@Field("ids[]") ids: ArrayList<String>): Response<MultiResponse>

    @GET("api/cancelationReason")
    suspend fun cancelationReason(): Response<CancelationReasonResponse>

    @FormUrlEncoded
    @POST("api/CancelationReport")
    suspend fun cancelationReport(@Field("user_id") user_id: String): Response<CancelationReportResponse>


    @GET("api/project")
    suspend fun project(): Response<ProjectResponse>

    @GET("api/property")
    suspend fun getProperty(): Response<PropertyResponse>
    @GET("api/plans")
    suspend fun plans(): Response<PlanResponse>

    @GET("api/post")
    suspend fun getPost(): Response<PostResponse>

    @GET("api/AllRoles")
    suspend fun getAllRoles(): Response<AllRolesAndAllPermissionsResponse>

    @GET("api/AllPermissions")
    suspend fun getAllPermissions(): Response<AllRolesAndAllPermissionsResponse>


    @FormUrlEncoded
    @POST("api/SalesProfileReport")
    suspend fun getSalesProfileReport(@Field("user_id") user_id: String):
            Response<SalesProfileReportResponse>


    @POST("api/MeetingReport")
    suspend fun getMeetingReport(): Response<MeetingReportResponse>

    @POST("api/login")
    suspend fun login(): Response<LoginResponse>

    @POST("api/logout")
    suspend fun logout(): Response<StatusResponse>

}