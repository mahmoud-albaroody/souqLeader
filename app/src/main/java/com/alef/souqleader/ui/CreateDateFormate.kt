package com.alef.souqleader.ui

import android.os.Build
import com.alef.souqleader.domain.model.AccountData
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


fun getCreatedAt1(created_at: String): String {
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
        val dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy 'at' h:mm a", Locale.ENGLISH)
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


fun getCreatedAt(timestamp: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        .withZone(ZoneOffset.UTC)

    val timeUtc = ZonedDateTime.parse(timestamp, inputFormatter)
    val nowUtc = ZonedDateTime.now(ZoneOffset.UTC)
    val zoneId = ZoneId.of("Africa/Cairo")
    val time = timeUtc.withZoneSameInstant(zoneId)
    val now = nowUtc.withZoneSameInstant(zoneId)

    val minutes = ChronoUnit.MINUTES.between(time, now)
    val hours = ChronoUnit.HOURS.between(time, now)
    val days = ChronoUnit.DAYS.between(time, now)

    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days >= 2 -> {
            val dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy 'at' h:mm a", Locale.ENGLISH)
            time.format(dateFormat)
        }
        else -> {
            if (AccountData.lang == "en") {
                when {
                    days == 0L -> "$hours hour${if (hours != 1L) "s" else ""} and $minutes minute${if (minutes != 1L) "s" else ""} ago"
                    hours == 0L -> "$minutes minute${if (minutes != 1L) "s" else ""} ago"
                    else -> "$days day${if (days != 1L) "s" else ""} and $hours hour${if (hours != 1L) "s" else ""} and $minutes minute${if (minutes != 1L) "s" else ""} ago"
                }
            } else {
                // Arabic
                when {
                    days == 0L -> "$hours ساعة و $minutes دقيقة مضت"
                    hours == 0L -> "$minutes دقيقة مضت"
                    else -> "$days يوم و $hours ساعة و $minutes دقيقة مضت"
                }
            }
        }
    }
}
