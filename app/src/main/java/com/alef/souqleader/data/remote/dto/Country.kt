package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData

data class Country(
    val id: Int,
    val name_ar: String,
    val name_en: String
) {
    fun getCountry(): String {
        return if (AccountData.lang == "en") {
            name_en
        } else {
            name_ar
        }
    }
}