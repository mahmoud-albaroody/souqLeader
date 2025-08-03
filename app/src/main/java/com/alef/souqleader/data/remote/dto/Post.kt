package com.alef.souqleader.data.remote.dto


import com.alef.souqleader.ui.getCreatedAt

data class Post(
    val comment: ArrayList<Comment>? = null,
    val created_at: String? = null,
    val id: Int? = null,
    val images: List<Image>? = null,
    var isLiked: Int? = null,
    var likes_count: Int? = null,
    var post: String? = null,
    val tenant_id: Int? = null,
    val updated_at: String? = null,
    val user_id: Int? = null,
    val user:User? = null,
    var postType:String?=null,

) {
    fun commentCount(): Int? {
        return comment?.size
    }

    fun getDate():String? {
        return created_at?.let { getCreatedAt(it) }
    }
}