package com.alef.souqleader.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.getCreatedAt
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class CancelationReason(
    val created_at: String,
    val id: Int,
    val title_ar: String,
    val title_en: String,
    val updated_at: String
) {
    fun getTitle(): String {
        return if (AccountData.lang == "en") {
            title_en
        } else {
            title_ar
        }
    }


        @RequiresApi(Build.VERSION_CODES.O)
        fun getDate():String? {
            return created_at?.let { getCreatedAt(it) }
        }

}