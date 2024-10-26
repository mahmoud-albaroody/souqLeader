package com.alef.souqleader.data.remote.dto

data class Action(
    val created_at: String,
    val id: Int,
    val lead_name: String,
    val note: String,
    val other_cancel_reason: String,
    val reminder_time: String,
    val sales: String,
    val status: String
)