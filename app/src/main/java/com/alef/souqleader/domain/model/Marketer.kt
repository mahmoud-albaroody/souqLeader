package com.alef.souqleader.domain.model

data class Marketer(
    val email: String,
    val id: Int,
    val name: String,
    val photo: String,
    val role: Role
)