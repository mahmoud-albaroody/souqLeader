package com.alef.souqleader.data.remote

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
import com.alef.souqleader.data.remote.dto.CommunicationWayResponse
import com.alef.souqleader.data.remote.dto.DelayReportResponse
import com.alef.souqleader.data.remote.dto.ForgetPasswordResponse
import com.alef.souqleader.data.remote.dto.GetClientResponse
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
import com.alef.souqleader.data.remote.dto.ProjectResponse
import com.alef.souqleader.data.remote.dto.ProjectsReportResponse
import com.alef.souqleader.data.remote.dto.PropertyResponse
import com.alef.souqleader.data.remote.dto.RegionsResponse
import com.alef.souqleader.data.remote.dto.SalesProfileReportResponse
import com.alef.souqleader.data.remote.dto.SalesResponse
import com.alef.souqleader.data.remote.dto.StatusResponse
import com.alef.souqleader.data.remote.dto.UnlockResponse
import com.alef.souqleader.data.remote.dto.UpdateLeadResponse
import com.alef.souqleader.data.remote.dto.UserDateResponse
import com.alef.souqleader.data.remote.dto.UserDetailsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface APIs {


    @GET("api/leadsByStatus")
    suspend fun leadsByStatus(
        @Query("status") status: String, @Query("page") page: Int
    ): Response<LeadsByStatusResponse>

    @FormUrlEncoded
    @POST("api/updateMulti")
    suspend fun updateMulti(
        @Field("ids[]") ids: Array<String>,
        @Field("status") status: String?,
        @Field("note") note: String?,
        @Field("reminder_time") reminder_time: String?,
        @Field("cancel_reason") cancel_reason: String?,
    ): Response<UpdateLeadResponse>

    @GET("api/cancelationReason")
    suspend fun cancelationReason(): Response<CancelationReasonResponse>

    @FormUrlEncoded
    @POST("api/CancelationReport")
    suspend fun cancelationReport(@Field("user_id") user_id: String): Response<CancelationReportResponse>


    @GET("api/project")
    suspend fun project(@Query("page") page: Int? = null): Response<ProjectResponse>

    @GET("api/property")
    suspend fun getProperty(@Query("page") page: Int? = null): Response<PropertyResponse>

    @GET("api/userDetails")
    suspend fun userDetails(@Query("user") user: String? = null): Response<UserDetailsResponse>

    @GET("api/regions")
    suspend fun regions(): Response<RegionsResponse>

    @GET("api/projectFilterData")
    suspend fun projectFilterData(): Response<ProjectFilterDataResponse>

    @GET("api/propertyFilterData")
    suspend fun propertyFilterData(): Response<ProjectFilterDataResponse>


    @GET("api/locationFilterData")
    suspend fun locationFilterData(
        @Query("country_id") finishing: String? = null,
        @Query("city_id") region: String? = null,
    ): Response<LocationFilterDataResponse>

    @GET("api/propertyLocationFilterData")
    suspend fun propertyLocationFilterData(
        @Query("country_id") countryId: String? = null,
        @Query("city_id") cityId: String? = null,
    ): Response<LocationFilterDataResponse>





    @GET("api/propertyView")
    suspend fun propertyView(): Response<RegionsResponse>

    @GET("api/propertyCategory")
    suspend fun propertyCategory(): Response<CategoryResponse>

    @GET("api/propertyFinishing")
    suspend fun propertyFinishing(): Response<RegionsResponse>


    @GET("api/propertyfilter")
    suspend fun propertyFilter(
        @Query("finishing") finishing: String? = null,
        @Query("region") region: String? = null,
        @Query("name") name: String? = null,
        @Query("view") view: String? = null,
        @Query("category") category: String? = null,
        @Query("type") type: String? = null,
        @Query("department") department: String? = null,
        @Query("country_id") countryId: String? = null,
        @Query("city_id") cityId: String? = null,
        @Query("area_id") areaId: String? = null,
        @Query("price_from") budget_from: String? = null,
        @Query("price_to") budget_to: String? = null,
    ): Response<PropertyResponse>



    @GET("api/projectsfilter")
    suspend fun projectFilter(
        @Query("title") title: String? = null,
        @Query("category") category: String? = null,
        @Query("country_id") countryId: String? = null,
        @Query("city_id") cityId: String? = null,
        @Query("area_id") areaId: String? = null,
        @Query("start_price_from") budget_from: String? = null,
        @Query("start_price_to") budget_to: String? = null,
    ): Response<ProjectResponse>

    @GET("api/projectSort")
    suspend fun projectSort(@Query("page") page: Int? = null): Response<ProjectResponse>

    @GET("api/propertySort")
    suspend fun propertySort(@Query("page") page: Int? = null): Response<PropertyResponse>

    @FormUrlEncoded
    @POST("api/leadDetails")
    suspend fun leadDetails(@Field("lead_id") leadId: String? = null): Response<LeadDetailsResponse>


    @GET("api/plans")
    suspend fun plans(): Response<PlanResponse>

    @GET("api/post")
    suspend fun getPost(@Query("page") page: Int? = null): Response<PostResponse>


    @GET("api/CompanyPost")
    suspend fun getCompanyPost(@Query("page") page: Int? = null): Response<PostResponse>


    @GET("api/AllRoles")
    suspend fun getAllRoles(): Response<AllRolesAndAllPermissionsResponse>

    @GET("api/AllPermissions")
    suspend fun getAllPermissions(): Response<AllRolesAndAllPermissionsResponse>


    @FormUrlEncoded
    @POST("api/SalesProfileReport")
    suspend fun getSalesProfileReport(@Field("user_id") user_id: String): Response<SalesProfileReportResponse>


    @POST("api/MeetingReport")
    suspend fun getMeetingReport(): Response<MeetingReportResponse>

    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") email: String, @Field("password") password: String
    ): Response<LoginResponse>



    @POST("api/userByToken")
    suspend fun getuserbytoken(): Response<LoginResponse>

    @GET("api/getClient/{name}")
    suspend fun getClient(@Path("name") name: String): Response<GetClientResponse>

    @POST("api/logout")
    suspend fun logout(): Response<StatusResponse>

    @FormUrlEncoded
    @POST("api/comment")
    suspend fun comment(
        @Field("comment") comment: String, @Field("post_id") post_id: String
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("api/CompanyComment")
    suspend fun companyComment(
        @Field("comment") comment: String, @Field("post_id") post_id: String
    ): Response<StatusResponse>

    @Multipart
    @POST("api/post")
    suspend fun addPost(
        @Part("post") post: RequestBody?, @Part images: ArrayList<MultipartBody.Part>?
    ): Response<StatusResponse>

    @Multipart
    @POST("api/CompanyPost")
    suspend fun addCompanyPost(
        @Part("post") post: RequestBody?, @Part images: ArrayList<MultipartBody.Part>?
    ): Response<StatusResponse>

    @FormUrlEncoded
    @POST("api/like")
    suspend fun like(
        @Field("like") like: String, @Field("post_id") post_id: String
    ): Response<AddLikeResponse>


    @FormUrlEncoded
    @POST("api/CompanyLike")
    suspend fun companyLike(
        @Field("like") like: String, @Field("post_id") post_id: String
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
    suspend fun duplicated(@Query("page") page: Int? = null): Response<LeadsByStatusResponse>

    @GET("api/FreshLeadStatus")
    suspend fun freshLeadStatus(): Response<LeadsByStatusResponse>

    @GET("api/DelayLeads")
    suspend fun delayLeads(@Query("page") page: Int? = null): Response<LeadsByStatusResponse>

    @FormUrlEncoded
    @POST("api/lead/{id}?_method=put")
    suspend fun updateLead(
        @Path("id") id: String,
        @Field("status") status: String?,
        @Field("note") note: String?,
        @Field("reminder_time") reminder_time: String?,
        @Field("cancel_reason") cancel_reason: String?,
    ): Response<UpdateLeadResponse>

    @GET("api/userData/{id}")
    suspend fun userData(
        @Path("id") id: String, @Query("action_page") action_page: String,
        @Query("activity_page") activity_page: String
    ): Response<UserDateResponse>

    @GET("api/channelStatistics")
    suspend fun channelStatistics(): Response<ChannelReportResponse>

    @POST("api/ProjectsReport")
    suspend fun projectsReport(): Response<ProjectsReportResponse>


    @POST("api/DelayReport")
    suspend fun delayReport(): Response<DelayReportResponse>

    @GET("api/AllUsers")
    suspend fun allUsers(): Response<AllUserResponse>

    @GET("api/addressFilter")
    suspend fun addressFilter(): Response<AddressFilterResponse>




    @GET("api/basicData")
    suspend fun basicData(): Response<BasicDataResponse>


    @GET("api/allJobs")
    suspend fun allJobs(@Query("page") page: Int): Response<AllJobResponse>


    @FormUrlEncoded
    @POST("api/jobapps")
    suspend fun jobapps(
        @Field("job_id") job_id: String?,
        @Field("category_id") category_id: String?,
        @Field("type_id") type_id: String?,
        @Field("career_level_id") career_level_id: String?,
        @Field("workplace_id") workplace_id: String?,
        @Field("name") name: String?,
        @Field("country_id") country_id: String?,
        @Field("city_id") city_id: String?,
        @Field("area_id") area_id: String?,
    ): Response<JobAppsResponse>

    @FormUrlEncoded
    @POST("api/unlock")
    suspend fun unlock(@Field("id") id: String?): Response<UnlockResponse>

    @FormUrlEncoded
    @POST("api/change-password")
    suspend fun changePassword(
        @Field("current_password") current_password: String?,
        @Field("new_password") new_password: String?,
        @Field("new_password_confirmation") new_password_confirmation: String?,
    ): Response<ChangePasswordResponse>

    @FormUrlEncoded
    @POST("api/reset-password")
    suspend fun resetPassword(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("password_confirmation") password_confirmation: String?,
        @Field("code") code: String?,
    ): Response<ForgetPasswordResponse>


    @FormUrlEncoded
    @POST("api/contactus")
    suspend fun contactus(
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("phone") phone: String?,
        @Field("organization_name") organization_name: String?,
        @Field("message") message: String?,
    ): Response<ForgetPasswordResponse>

    @FormUrlEncoded
    @POST("api/checkcode")
    suspend fun checkCode(
        @Field("code") code: String?,
    ): Response<ForgetPasswordResponse>

    @FormUrlEncoded
    @POST("api/forget-password")
    suspend fun forgetPassword(
        @Field("email") email: String?,
    ): Response<ForgetPasswordResponse>


    @GET("api/Leadsfilter")
    suspend fun leadsFilter(
        @Query("phone") phone: String? = null,
        @Query("status") status: String? = null,
        @Query("name") name: String? = null,
        @Query("note") note: String? = null,
        @Query("channel") channel: String? = null,
        @Query("sales") sales: String? = null,
        @Query("project") project: String? = null,
        @Query("budget_from") budget_from: String? = null,
        @Query("budget_to") budget_to: String? = null,
        @Query("marketer") marketer: String? = null,
        @Query("communication_way") communication_way: String? = null,
        @Query("region") region: String? = null,
        @Query("created_from") created_from: String? = null,
        @Query("created_to") created_to: Boolean? = null,
        @Query("action_date_from") action_date_from: Boolean? = null,
        @Query("action_date_to") action_date_to: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("searchField") searchField: String? = null
    ): Response<LeadsByStatusResponse>


}

