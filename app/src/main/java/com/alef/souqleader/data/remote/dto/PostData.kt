package com.alef.souqleader.data.remote.dto

import com.alef.souqleader.data.remote.Info

data class PostData(
    val info: Info?=null,
    var data: ArrayList<Post>? = null,


)
