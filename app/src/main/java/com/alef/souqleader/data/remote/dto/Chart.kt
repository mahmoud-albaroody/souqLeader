package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class Chart(
    val count: Float?=null,
    val date: String?=null,
    val actions_count: Float?=null,
    val title_ar: String?=null,
    val title_en: String?=null,
    val id: String?=null,
    val status_id: Int?=null,
    val channel_title: String?=null,
    val fresh_leads: Int?=null,
    val percentage_leads: String?=null,
    val total: Float?=null,
    val total_leads: Float?=null,
    val delay_actions_count: Float?=null
) {
    fun getCount(): Float {
        return count ?: actions_count ?: total_leads ?: delay_actions_count ?: 0f
    }

    fun getTitle(): String? {
        return if (channel_title.isNullOrEmpty()) {
            if (AccountData.lang == "en") {
                title_en
            } else {
                title_ar
            }
        } else {
            channel_title
        }
    }
}