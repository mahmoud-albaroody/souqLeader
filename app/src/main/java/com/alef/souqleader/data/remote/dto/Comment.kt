package com.alef.souqleader.data.remote.dto

data class Comment(
    val comment: String,
    val comment_id: Int,
    val created_at: String,
    val id: Int,
    val post_id: Int,
    val tenant_id: Int,
    val updated_at: String,
    val user_id: Int,
    val username: String?,
    val user_Image: String?,
)