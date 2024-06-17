package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class StatusCounter(
    val actions_count: String,
    val has_pending: Boolean,
    val title_ar: String,
    val title_en: String
) {
    fun getTitle(): String {
        return if (AccountData.lang == "ar") {
            title_ar
        } else {
            title_en
        }
    }
}