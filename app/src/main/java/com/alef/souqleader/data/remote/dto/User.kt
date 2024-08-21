package com.alef.souqleader.data.remote.dto

data class User(
    val image: String?=null,
    val role: String,
    val created_at: String,
    val deactivate: Int,
    val deleted_at: Any,
    val email: String,
    val email_verified_at: Any,
    val gender: String,
    val id: Int,
    val is_online: Int,
    val lang: String,
    val name: String,
    val phone: String,
    val photo: String,
    val reports_to: Int,
    val reset_code: Any,
    val reset_date: Any,
    val role_id: Int,
    val updated_at: String
)