package com.alef.souqleader.data.remote.dto


import com.alef.souqleader.ui.getCreatedAt

data class Action(
    val created_at: String?,
    val id: Int?,
    val lead_name: String?,
    val note: String?,
    val other_cancel_reason: String?,
    val reminder_time: String?,
    val sales: String?,
    val status: String?,
    val type: String?
){


    fun getDate():String? {
        return created_at?.let { getCreatedAt(it) }
    }

}