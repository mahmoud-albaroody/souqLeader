package com.alef.souqleader.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.alef.souqleader.ui.getCreatedAt
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate():String? {
        return created_at?.let { getCreatedAt(it) }
    }

}