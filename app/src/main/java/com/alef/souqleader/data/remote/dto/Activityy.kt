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

data class Activityy(
    val activity_by: ActivityBy?,
    val created_at: String,
    val description: String,
    val description_ar: String,
    val id: Int,
    val link: String,
    val page_id: Int,
    val page_name: String
){
    fun getDescriptions(): String {
        return if(AccountData.lang =="en") {
            description
        }else{
            description_ar
        }
    }


        @RequiresApi(Build.VERSION_CODES.O)
        fun getDate(): String {
            return getCreatedAt(created_at)
        }


}