package com.alef.souqleader.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.alef.souqleader.domain.model.AccountData
import java.time.Duration
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
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCreatedAt(created_at: String): String {
            val formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
            val dateTime = ZonedDateTime.parse(created_at, formatter)
            val now = ZonedDateTime.now(ZoneId.of("UTC"))

            val duration = Duration.between(dateTime, now)

            val days = duration.toDays()
            val hours = duration.minusDays(days).toHours()
            val minutes = duration.minusDays(days).minusHours(hours).toMinutes()

            return "${days} day${if (days != 1L) "s" else ""} and " +
                    "${hours} hour${if (hours != 1L) "s" else ""} and " +
                    "${minutes} minute${if (minutes != 1L) "s" else ""} ago"
        }


}