package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class LeadStatus(
    val icon: String,
    val icon_name: String,
    val id: Int,
    val leads_count: String?,
    val percentage: Int,
    val title_ar: String,
    val title_en: String
) {
    fun getTitle(): String {
        if (AccountData.lang == "ar") {
            return title_ar
        } else {
            return title_en
        }
    }
    fun getPer():Float{
     return   percentage/100.0.toFloat()
    }
}