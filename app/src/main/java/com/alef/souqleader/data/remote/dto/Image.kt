package com.alef.souqleader.data.remote.dto

import java.io.Serializable

data class Image(
    val `file`: String?,
    val project_id: Int?,
    val created_at: String?,
    val id: Int?,
    val image: String?,
    val post_id: Int?,
    val updated_at: String?
)