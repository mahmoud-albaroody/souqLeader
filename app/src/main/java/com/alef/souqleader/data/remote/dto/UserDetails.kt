package com.alef.souqleader.data.remote.dto

data class UserDetails(
    val actions_count: String?,
    val activity_count: String?,
    val sales_report_count: String?,
    val email: String,
    val id: Int,
    val name: String,
    val permissions: List<Permission>,
    val photo: String,
    val role_id: Int,
    val role_name: String
)