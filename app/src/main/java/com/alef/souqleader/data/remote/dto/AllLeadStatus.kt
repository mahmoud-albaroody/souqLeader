package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class AllLeadStatus(
    val icon: String? = null,
    val icon_name: String? = null,
    val id: Int? = null,
    val leads_count: String? = null,
    val percentage: Int? = null,
    val title_ar: String? = null,
    val title_en: String? = null
) {
    fun getTitle(): String {
        return if (AccountData.lang == "ar") {
            title_ar ?: ""
        } else {
            title_en ?: ""
        }
    }

    fun getPer(): Float {
        return percentage?.div(100.0.toFloat()) ?: 0f
    }
}