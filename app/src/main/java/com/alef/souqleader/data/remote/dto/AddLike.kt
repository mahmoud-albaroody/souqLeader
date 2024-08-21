package com.alef.souqleader.data.remote.dto

data class AddLike(
    val like: String?=null,
    val post_id: String?=null,
    val tenant_id: Int?=null,
    val user_id: Int?=null
)