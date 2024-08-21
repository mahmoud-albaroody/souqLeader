package com.alef.souqleader.data.remote.dto

data class PostData (
    val current_page:Int,
    var data: ArrayList<Post>? = null
)