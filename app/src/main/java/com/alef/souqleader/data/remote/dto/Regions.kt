package com.alef.souqleader.data.remote.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.alef.souqleader.domain.model.AccountData
import com.alef.souqleader.ui.getCreatedAt
import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
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

}