package com.alef.souqleader.data.remote.dto

data class Lead(
    val action_date: String,
    val cancel_reason: String?,
    val channel: String?,
    val communication_way: String?,
    val created_at: String,
    val created_by: Int,
    val email: String,
    val id: Int,
    val marketer_id: String?,
    val name: String,
    val note: String,
    val phone: String,
    val project_id: String?,
    val sales_id: String,
    val status: String,
    val updated_at: String
)