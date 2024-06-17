package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class Plan(
    val currency: String,
    val frequency: String,
    val name_ar: String,
    val name_en: String,
    val price: Int,
    val trail_days: String
) {
    fun getPlanName(): String {
        return if (AccountData.lang == "ar") {
            name_ar
        } else {
            name_en
        }
    }
}