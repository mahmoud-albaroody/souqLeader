package com.alef.souqleader.data.remote.dto

data class PostData(
    val current_page: Int?=null,
    var data: ArrayList<Post>? = null,
    val last_page: Int?=null


)
