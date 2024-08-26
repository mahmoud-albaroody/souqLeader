package com.alef.souqleader.data.remote.dto

data class Lead(
    val action_date: String,
    val budget: String?,
    val campaign_id: Int?,
    val campaign_name: String,
    val cancel_reason: String,
    val channel_id: Int,
    val channel_name: String,
    val communication_way: String?,
    val created_by: Int,
    val created_by_name: String,
    val email: String,
    val id: Int,
    val marketer_id: Int,
    val marketer_name: String,
    val name: String?,
    val note: String?,
    val phone: String?,
    val project_id: Int?,
    var project_name: String?,
    val sales_id: Int?,
    val sales_name: String?,
    val status: Int?,
    var selected: Boolean,
    val channel: Any,
    val created_at: String?,
    val is_duplicated: Int?,
    val updated_at: String?

)