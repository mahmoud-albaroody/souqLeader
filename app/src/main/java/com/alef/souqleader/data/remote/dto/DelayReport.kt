package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class DelayReport(
    val delay_actions_count: Float,
    val delay_actions_list: List<DelayActions>,
    val status_id: Int,
    val title_ar: String,
    val title_en: String
) {
    fun getTitle(): String {
        return if (AccountData.lang == "en") {
            title_en
        } else {
            title_ar
        }
    }
}