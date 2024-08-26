package com.alef.souqleader.data.remote.dto

data class Comment(
    val comment: String?=null,
    val comment_id: Int?=null,
    val created_at: String?=null,
    val id: Int?=null,
    val post_id: Int?=null,
    val tenant_id: Int?=null,
    val updated_at: String?=null,
    val user_id: Int?=null,
    val username: String?=null,
    val user_Image: String?=null,
    val user:User?=null
)