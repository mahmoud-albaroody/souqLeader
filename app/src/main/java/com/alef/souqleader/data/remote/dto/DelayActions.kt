package com.alef.souqleader.data.remote.dto

data class DelayActions(
    val created_at: String?=null,
    val id: Int,
    val lead_name: String?=null,
    val note: String?=null,
    val other_cancel_reason: Any?=null,
    val reminder_time: String?=null,
    val sales: String?=null,
    val status: String?=null
)