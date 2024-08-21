package com.alef.souqleader.data.remote.dto

import java.io.Serializable

data class Permissions(
    val create: Boolean,
    val delete: Boolean,
    val read: Boolean,
    val update: Boolean
):Serializable