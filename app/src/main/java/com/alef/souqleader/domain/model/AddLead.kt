package com.alef.souqleader.domain.model

import retrofit2.http.Field

data class AddLead(
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var note: String? = null,
    var marketer_id: Int? = null,
    var sales_id: Int? = null,
    var project_id: Int? = null,
    var channel: Int? = null,
    var communication_way: Int? = null,
    var cancel_reason: String? = null,
    val action_date: String? = null,
    var budget: String? = null,
    var campaign_id:Int?=null,
    var is_fresh: Boolean

)
