package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.data.remote.Info

data class UserDate(
    val actions: List<Action>?=null,
    val user:User?=null,
    val actions_count: String?=null,
    val sales_report_count: String?=null,
    val activities: List<Activityy>?=null,
    val activities_count: String?=null,
    val actions_pagination: Info?=null,
    val activities_pagination: Info?=null

)