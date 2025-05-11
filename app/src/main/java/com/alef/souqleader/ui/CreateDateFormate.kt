package com.alef.souqleader.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.alef.souqleader.domain.model.AccountData
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getCreatedAt(created_at: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val zoneId = ZoneId.of("Africa/Cairo")

    val dateTime = LocalDateTime.parse(
        created_at.substringBefore("T") + " " +
                created_at.substringAfter("T").substringBefore("."),
        formatter
    ).atZone(zoneId)

    val now = ZonedDateTime.now(zoneId)
    val duration = Duration.between(dateTime, now)

    val days = duration.toDays()
    val hours = duration.minusDays(days).toHours()
    val minutes = duration.minusDays(days).minusHours(hours).toMinutes()

    // If more than 2 days, return formatted date like "12Apr 2025 at 6:22 PM"
    if (days > 1) {
        val dateFormat = DateTimeFormatter.ofPattern("ddMMM yyyy 'at' h:mm a", Locale.ENGLISH)
        return dateTime.format(dateFormat)
    }else {

        return if (AccountData.lang == "en") {
            when {
                days == 0L -> "$hours hour${if (hours != 1L) "s" else ""} and $minutes minute${if (minutes != 1L) "s" else ""} ago"
                hours == 0L -> "$minutes minute${if (minutes != 1L) "s" else ""} ago"
                else -> "$days day${if (days != 1L) "s" else ""} and $hours hour${if (hours != 1L) "s" else ""} and $minutes minute${if (minutes != 1L) "s" else ""} ago"
            }
        } else {

            // Arabic text
            when {
                days == 0L -> "$hours ساعة و $minutes دقيقة مضت"
                hours == 0L -> "$minutes دقيقة مضت"
                else -> "$days يوم و $hours ساعة و $minutes دقيقة مضت"
            }
        }
    }
}