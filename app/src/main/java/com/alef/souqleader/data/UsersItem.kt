package com.alef.souqleader.data


import com.alef.souqleader.domain.model.AccountData

data class UsersItem(
    val id: Int,
    val name_ar: String,
    val name_en: String,
    val users: List<User>
) {
    fun getName(): String {
        return if (AccountData.lang == "en") {
            name_en
        } else {
            name_ar
        }
    }
}