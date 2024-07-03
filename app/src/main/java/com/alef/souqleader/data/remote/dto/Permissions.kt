package com.alef.souqleader.data.remote.dto

data class Permissions(
    val create: Boolean,
    val delete: Boolean,
    val read: Boolean,
    val update: Boolean
)