package com.alef.souqleader.data.remote

import com.alef.souqleader.data.remote.dto.AddLeadResponse
import com.alef.souqleader.data.remote.dto.AddLikeResponse
import com.alef.souqleader.data.remote.dto.AllRolesAndAllPermissionsResponse
import com.alef.souqleader.data.remote.dto.CampaignResponse
import com.alef.souqleader.data.remote.dto.CancelationReasonResponse
import com.alef.souqleader.data.remote.dto.CancelationReportResponse
import com.alef.souqleader.data.remote.dto.ChannelReportResponse
import com.alef.souqleader.data.remote.dto.ChannelResponse
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.DelayReportResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
import com.alef.souqleader.data.remote.dto.LeadsByStatusResponse
import com.alef.souqleader.data.remote.dto.LeadsStatusResponse
import com.alef.souqleader.data.remote.dto.LoginResponse
import com.alef.souqleader.data.remote.dto.MarketerResponse
import com.alef.souqleader.data.remote.dto.MeetingReportResponse
import com.alef.souqleader.data.remote.dto.MultiResponse
import com.alef.souqleader.data.remote.dto.PlanResponse
import com.alef.souqleader.data.remote.dto.PostResponse
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.ProjectsReportResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.data.remote.dto.UserDateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface APIs {


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

    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("api/getClient/{name}")
    suspend fun getClient(@Path("name") name: String): Response<GetClientResponse>

    @POST("api/logout")
    suspend fun logout(): Response<StatusResponse>

    @FormUrlEncoded
    @POST("api/comment")
    suspend fun comment(
        @Field("comment") comment: String,
        @Field("post_id") post_id: String
    ): Response<StatusResponse>

    @Multipart
    @POST("api/post")
    suspend fun addPost(
        @Part("post") post: RequestBody?,
        @Part images: ArrayList<MultipartBody.Part>?
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("api/like")
    suspend fun like(
        @Field("like") like: String,
        @Field("post_id") post_id: String
    ): Response<AddLikeResponse>

    @FormUrlEncoded
    @POST("api/UpdateFcmToken")
    suspend fun updateFcmToken(
        @Field("fcm_token") like: String,
    ): Response<StatusResponse>

    @GET("api/communicationWay")
    suspend fun communicationWay(): Response<CommunicationWayResponse>

    @GET("api/AllMarketer")
    suspend fun allMarketer(): Response<MarketerResponse>

    @GET("api/AllSales")
    suspend fun allSales(): Response<SalesResponse>

    @GET("api/campaign")
    suspend fun campaign(): Response<CampaignResponse>

    @GET("api/channel")
    suspend fun channel(): Response<ChannelResponse>

    @FormUrlEncoded
    @POST("api/lead")
    suspend fun lead(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("phone") phone: String?,
        @Field("note") note: String?,
        @Field("marketer_id") marketer_id: Int?,
        @Field("sales_id") sales_id: Int?,
        @Field("project_id") project_id: Int?,
        @Field("channel") channel: Int?,
        @Field("communication_way") communication_way: Int?,
        @Field("cancel_reason") cancel_reason: String?,
        @Field("action_date") action_date: String?,
        @Field("budget") budget: String?,
        @Field("is_fresh") is_fresh: Boolean?,
    ): Response<AddLeadResponse>

    @GET("api/leadStatus")
    suspend fun leadStatus(): Response<LeadsStatusResponse>

    @GET("api/ColdLeadStatus")
    suspend fun coldLeadStatus(): Response<LeadsByStatusResponse>

    @GET("api/duplicated")
    suspend fun duplicated(): Response<LeadsByStatusResponse>

    @GET("api/FreshLeadStatus")
    suspend fun freshLeadStatus(): Response<LeadsByStatusResponse>

    @GET("api/DelayLeads")
    suspend fun delayLeads(): Response<LeadsByStatusResponse>

    @FormUrlEncoded
    @POST("api/lead/{id}?_method=put")
    suspend fun updateLead(
        @Path("id") id: String,
        @Field("status") status: String?,
        @Field("note") note: String?,
        @Field("reminder_time") reminder_time: String?,
        @Field("cancel_reason") cancel_reason: String?,
    ): Response<UpdateLeadResponse>

    @FormUrlEncoded
    @GET("api/userData/{id}")
    suspend fun userData(@Path("id") id: String): Response<UserDateResponse>
    @GET("api/channelStatistics")
    suspend fun channelStatistics(): Response<ChannelReportResponse>
    @POST("api/ProjectsReport")
    suspend fun projectsReport(): Response<ProjectsReportResponse>
    @POST("api/DelayReport")
    suspend fun delayReport(): Response<DelayReportResponse>


}

