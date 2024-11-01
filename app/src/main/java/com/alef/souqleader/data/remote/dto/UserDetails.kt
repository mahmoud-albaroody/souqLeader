package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.data.remote.Info

data class UserDetails(
    val actions_count: String?,
    val activity_count: String?,
    val activities:List<Activityy>?,
    val actions:List<Action>?,
    val sales_report_count: String?,
    val email: String,
    val id: Int,
    val name: String,
    val permissions: List<Permission>,
    val photo: String,
    val role_id: Int,
    val role_name: String,val actions_pagination:Info
)