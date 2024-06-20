package com.alef.souqleader.data.remote.dto

data class Login(
    val access_token: String,
    val email: String,
    val id: Int,
    val name: String,
    val permissions: List<String>,
    val photo: String,
    val role_id: Int,
    val role_name: String
)