package com.alef.souqleader.domain.model

data class Campaign(
    val created_at: String,
    val id: Int,
    val title_ar: String,
    val title_en: String,
    val updated_at: String
) {
    fun getTitle(): String {
        return if (AccountData.lang == "en") {
            title_en
        } else {
            title_ar
        }
    }
}