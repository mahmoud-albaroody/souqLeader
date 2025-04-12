package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.domain.model.AccountData
import java.io.Serializable

data class Regions(
    val created_at: String?,
    val id: Int?,
    val name_ar: String?,
    val name_en: String?,
    val updated_at: String?,
    val country_id:String?,
    val city_id:String?,
    val status:String?,
    val city:City?,
    val category: Category?,
) {
    fun getTitle(): String? {
        return if (AccountData.lang == "ar") {
            name_ar
        } else {
            name_en
        }
    }
}