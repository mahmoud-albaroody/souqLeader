package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.ui.getCreatedAt

data class User(
    val image: String? = null,
    val role: String? = null,
    val created_at: String? = null,
    val deactivate: Int? = null,
    val deleted_at: Any? = null,
    val email: String? = null,
    val email_verified_at: Any? = null,
    val gender: String? = null,
    val id: Int? = null,
    val is_online: Int? = null,
    val lang: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val photo: String? = null,
    val reports_to: Int? = null,
    val reset_code: Any? = null,
    val reset_date: Any? = null,
    val role_id: Int? = null,
    val updated_at: String? = null,

    )
{


    fun getDate():String? {
        return created_at?.let { getCreatedAt(it) }
    }

}

