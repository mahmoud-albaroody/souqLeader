package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class ChannelChart(
    val lead_count: Float,
    val title_ar: String,
    val title_en: String,
    val id: Int
) {
    fun title(): String {
        return if (AccountData.lang == "ar") {
            title_ar
        } else {
            title_en
        }
    }
}