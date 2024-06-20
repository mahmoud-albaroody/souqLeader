package com.alef.souqleader.data.remote.dto

import com.google.gson.annotations.SerializedName

open class StatusResponse(
    @SerializedName("status")
    val status: Boolean = false,

    @SerializedName("message")
    val message: String? = ""
)

data class LeadsByStatusResponse(
    var data: ArrayList<Lead>? = null
) : StatusResponse()

data class MultiResponse(
    var data: Boolean
) : StatusResponse()


data class ProjectResponse(
    var data: ArrayList<Project>? = null
)

data class PlanResponse(
    var data: ArrayList<Plan>? = null
)

data class PostResponse(
    var data: ArrayList<Post>? = null
)

data class AllRolesAndAllPermissionsResponse(
    var data: ArrayList<AllRolesAndAllPermissions>? = null
)

data class LeadsStatusResponse(
    var data: ArrayList<LeadStatus>? = null
) : StatusResponse()


data class CancelationReasonResponse(
    var data: ArrayList<CancelationReason>? = null
) : StatusResponse()

data class CancelationReportResponse(
    var data: CancelationReport? = null
) : StatusResponse()


data class SalesProfileReportResponse(
    var data: SalesProfileReport? = null
) : StatusResponse()

data class LoginResponse(
    var data: Login? = null
) : StatusResponse()

data class MeetingReportResponse(
    var data: MeetingReport? = null
) : StatusResponse()

data class PropertyResponse(
    var data: ArrayList<Property>? = null
) : StatusResponse()

data class SalesProfileReportRequest(var user_id: String) {

}

