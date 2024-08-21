package com.alef.souqleader.domain.model

data class Sales(
    val email: String,
    val id: Int,
    val name: String,
    val photo: String,
    val role: Role
)