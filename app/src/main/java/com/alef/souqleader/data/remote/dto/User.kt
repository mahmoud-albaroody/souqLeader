package com.alef.souqleader.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.alef.souqleader.data.Role
import com.google.gson.annotations.SerializedName
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class User(
    val image: String? = null,
    val role: String? = null,
    val created_at: String? = null,
    val deactivate: Int? = null,
    val deleted_at: Any? = null,
    val email: String? = null,
    val email_verified_at: Any? = null,
    val gender: String? = null,
    val id: Int? = null,
    val is_online: Int? = null,
    val lang: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val photo: String? = null,
    val reports_to: Int? = null,
    val reset_code: Any? = null,
    val reset_date: Any? = null,
    val role_id: Int? = null,
    val updated_at: String? = null,

    )
{

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

