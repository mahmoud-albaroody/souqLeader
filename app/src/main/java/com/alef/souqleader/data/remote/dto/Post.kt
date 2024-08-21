package com.alef.souqleader.data.remote.dto

data class Post(
    val comment: List<Comment>,
    val created_at: String,
    val id: Int,
    val images: List<Image>? = null,
    var isLiked: Int,
    val likes_count: String,
    val post: String,
    val tenant_id: Int,
    val updated_at: String,
    val user_id: Int,
    val user:User
) {
    fun commentCount(): Int {
        return comment.size
    }
}