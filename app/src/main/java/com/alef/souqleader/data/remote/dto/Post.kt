package com.alef.souqleader.data.remote.dto

data class Post(
    val comment: List<Comment>,
    val created_at: String,
    val id: Int,
    val images: List<Image>? = null,
    val isLiked: Int,
    val likes_count: String,
    val post: String,
    val tenant_id: Int,
    val updated_at: String,
    val user_id: Int
) {
    fun commentCount(): Int {
        return comment.size
    }
}