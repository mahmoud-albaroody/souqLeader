package com.alef.souqleader.data.remote.dto

data class Login(
    val access_token: String? = null,
    val email: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val permissions: List<ModulePermission>? = null,
    val photo: String? = null,
    val role_id: Int? = null,
    val role_name: String? = null
)