package com.alef.souqleader.data.remote.dto

data class NotificationData(
    val activity_by: String,
    val created_at: String,
    val description: String,
    val description_ar: String,
    val id: String,
    val link: String,
    val page_id: String,
    val page_name: String,
    val status: String,
    val updated_at: String,
    val user_id: String
)