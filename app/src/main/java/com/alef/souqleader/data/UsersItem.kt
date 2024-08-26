package com.alef.souqleader.data

data class UsersItem(
    val id: Int,
    val name_ar: String,
    val name_en: String,
    val users: List<User>
)