package com.alef.souqleader.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.alef.souqleader.domain.model.AccountData
import java.io.Serializable
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class Regions(
    val created_at: String?,
    val id: Int?,
    val name_ar: String?,
    val name_en: String?,
    val updated_at: String?,
    val country_id:String?,
    val city_id:String?,
    val status:String?,
    val city:City?,
    val category: Category?,
) {
    fun getTitle(): String? {
        return if (AccountData.lang == "ar") {
            name_ar
        } else {
            name_en
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