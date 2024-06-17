package com.alef.souqleader.data.remote.dto

data class CancelReason(
    val lead: Lead,
    val other_cancel_reason: String,
    val reasons: String
)