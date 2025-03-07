package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.data.UsersItem
import com.alef.souqleader.data.remote.Info
import com.alef.souqleader.domain.model.Campaign
import com.alef.souqleader.domain.model.Channel
import com.alef.souqleader.domain.model.Client
import com.alef.souqleader.domain.model.CommunicationWay
import com.alef.souqleader.domain.model.Marketer
import com.alef.souqleader.domain.model.Sales

open class StatusResponse(
    val status: Boolean = false,
    val message: Any? = ""
)
open class StatusFilterResponse(
    val status: Int ?=null,
    val message: Any? = ""
)
data class LeadsByStatusResponse(
    val data: ArrayList<Lead>? = null,
    val info: Info? = null
) : StatusResponse()


data class ChangePasswordResponse(
    val data: Boolean? = null,
) : StatusResponse()

data class ForgetPasswordResponse(
    val data: Boolean = false,
) : StatusResponse()


data class DelayReportResponse(
    var data: ArrayList<DelayReport>? = null
) : StatusResponse()

data class AllUserResponse(
    var data: ArrayList<UsersItem>? = null
) : StatusResponse()

data class AddressFilterResponse(
    var data: AddressFilter? = null
) : StatusResponse()

data class BasicDataResponse(
    var data: BasicData? = null
) : StatusResponse()

data class JobAppsResponse(
    var data: ArrayList<Jobapps>? = null
) : StatusResponse()


data class AllJobResponse(
    var data: ArrayList<JopPersion>? = null
) : StatusResponse()


data class ProjectsReportResponse(
    var data: MeetingReport? = null
) : StatusResponse()

data class UserDateResponse(
    var data: UserDate? = null
) : StatusResponse()


data class ChannelResponse(
    var data: ArrayList<Channel>? = null
) : StatusResponse()

data class AddLeadResponse(
    var data: Boolean
) : StatusResponse()

data class CampaignResponse(
    var data: ArrayList<Campaign>? = null
) : StatusResponse()

data class MarketerResponse(
    var data: ArrayList<Marketer>? = null
) : StatusResponse()

data class SalesResponse(
    var data: ArrayList<Sales>? = null
) : StatusResponse()


data class CommunicationWayResponse(
    var data: ArrayList<CommunicationWay>? = null
) : StatusResponse()


data class UpdateLeadResponse(
    var data: Boolean
) : StatusResponse()

data class ProjectResponse(
    var data: ArrayList<Project>? = null,
    val info: Info? = null
)

data class AddLikeResponse(
    var data: Boolean? = null
) : StatusResponse()

data class PlanResponse(
    var data: ArrayList<Plan>? = null
) : StatusResponse()

data class PostResponse(
    var data: ArrayList<Post>,
    val info: Info? = null
) : StatusResponse()

data class AllRolesAndAllPermissionsResponse(
    var data: ArrayList<AllRolesAndAllPermissions>? = null
) : StatusResponse()

data class LeadsStatusResponse(
    var data: ArrayList<AllLeadStatus>? = null
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

data class UserDetailsResponse(
    var data: UserDetails? = null
) : StatusResponse()

data class RegionsResponse(
    var data: ArrayList<Regions>? = null
) : StatusResponse()
data class CategoryResponse(
    var data: ArrayList<Regions>? = null
) : StatusFilterResponse()


data class MeetingReportResponse(
    var data: MeetingReport? = null
) : StatusResponse()

data class ChannelReportResponse(
    var data: ArrayList<Chart>? = null
) : StatusResponse()

data class PropertyResponse(
    var data: ArrayList<PropertyObject>? = null,
    var info: Info? = null,
) : StatusResponse()

data class LeadDetailsResponse(
    var data: LeadDetails? = null
) : StatusResponse()


data class SalesProfileReportRequest(var user_id: String) {

}

data class GetClientResponse(
    var data: Client? = null
) : StatusResponse()
